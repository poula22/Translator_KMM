//
//  IosVoiceToTextParser.swift
//  iosApp
//
//  Created by poula george on 03/04/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared
import Speech
import Combine

class IosVoiceToTextParser : VoiceToTextParser, ObservableObject {
    private let _state = IOSMutableStateFlow(
        initialValue: VoiceToTextState(result: "", error: nil, powerRatio: 0.0, isSpeaking: false)
    )
    var state: CommonStateFlow<VoiceToTextState> {_state}
    
    private var micObserver = MicrophonePowerObserver()
    var micPowerRatio: Published<Double>.Publisher {micObserver.$micPowerRatio}
    private var micPowerCancellable: AnyCancellable?
    
    private var recognizer: SFSpeechRecognizer?
    private var audiuonEngine: AVAudioEngine?
    private var inputNode: AVAudioInputNode?
    private var audioBufferRequest: SFSpeechAudioBufferRecognitionRequest?
    private var recognitionTask: SFSpeechRecognitionTask?
    private var audioSession: AVAudioSession?
    
    func cancel() {
       //not needed on ios
    }
    
    func reset() {
        self.stopListening()
        _state.value = VoiceToTextState(result: "", error: nil, powerRatio: 0.0, isSpeaking: false)
    }
    
    func startListening(langCode: String) {
        updateState(error: nil)
        let chosenLocal = Locale.init(identifier: langCode)
        let supportedLocale = SFSpeechRecognizer.supportedLocales().contains(chosenLocal) ? chosenLocal: Locale.init(identifier: "en-us")
        self.recognizer = SFSpeechRecognizer(locale: supportedLocale)
        
        guard recognizer?.isAvailable == true else {
            updateState(error: "speech recognizer is not available")
            return
        }
        
        audioSession = AVAudioSession.sharedInstance()
        
        self.requestPermission { [weak self] in
            self?.audioBufferRequest = SFSpeechAudioBufferRecognitionRequest()
            
            guard let audioBufferRequest = self?.audioBufferRequest else {
                return
            }
            self?.recognitionTask = self?.recognizer?.recognitionTask(with: audioBufferRequest) {
                [weak self] result, error in
                guard let result = result else {
                    self?.updateState(error: error?.localizedDescription)
                }
                
                if result.isFinal {
                    self?.updateState(error: result.bestTranscription.formattedString)
                }
            }
            
            self?.audiuonEngine = AVAudioEngine()
            self?.inputNode = self?.audiuonEngine?.inputNode
            
            let recordingFormat = self?.inputNode?.outputFormat(forBus: 0)
            self?.inputNode?.installTap(onBus: 0, bufferSize: 1024, format: recordingFormat) { buffer, _ in
                self?.audioBufferRequest?.append(buffer)
            }
            
            self?.audiuonEngine?.prepare()
            
            do {
                try self?.audioSession?.setCategory(.playAndRecord, mode: .spokenAudio, options: .duckOthers)
                try self?.audioSession?.setActive(true, options: .notifyOthersOnDeactivation)
                
                self?.micObserver.startObservation()
                try self?.audiuonEngine?.start()
                self?.updateState(isSpeaking: true)
                
                self?.micPowerCancellable = self?.micPowerRatio
                    .sink { [weak self] ratio in
                        self?.updateState(updateState(powerRatio: ratio))
                    }
            }catch {
                self?.updateState(error: error.localizedDescription, updateState(isSpeaking: false))
            }
        }
    }
    
    func stopListening() {
        self.updateState(isSpeaking: false)
               
        micPowerCancellable = nil
        micObserver.release()
               
        audioBufferRequest?.endAudio()
        audioBufferRequest = nil
               
        audioEngine?.stop()
        audioEngine = nil
               
        inputNode?.removeTap(onBus: 0)
               
        try? audioSession?.setActive(false)
        audioSession = nil
    }
    
    private func requestPermission(onGranted: @escaping () -> Void) {
        audioSession?.requestRecordPermission { [weak self] wasGranted in
            if !wasGranted {
                self?.updateState(error: "you need to update permission to record your vocie")
                self?.stopListening()
                return
            }
            SFSpeechRecognizer.requestAuthorization { [weak self] status in
                DispatchQueue.main.async {
                    if status != .authorized {
                        self?.updateState(error: "you need to grant permission to transcribe audio.")
                        self?.stopListening()
                        return
                    }
                    onGranted()
                }
            }
        }
        
    }
    
    private func updateState(
        result: String? = nil,
        error: String?=nil,
        powerRatio: CGFloat? = nil,
        isSpeaking: Bool? = nil
    ) {
        let currentState = _state.value
        _state.value = VoiceToTextState(
            result: result ?? currentState?.result ?? "",
            error: error,
            powerRatio: Float(powerRatio ?? CGFloat(currentState?.powerRatio ?? 0.0)),
            isSpeaking: isSpeaking ?? currentState?.isSpeaking ?? false
        )
    }
    
}
