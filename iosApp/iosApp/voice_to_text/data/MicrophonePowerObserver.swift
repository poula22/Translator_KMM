//
//  MicrophonePowerObserver.swift
//  iosApp
//
//  Created by poula george on 03/04/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared
import Speech
import Combine


class MicrophonePowerObserver: ObservableObject {
    private var cancellable: Cancellable? = nil
    private var audioRecorder: AVAudioRecorder? = nil
    
    @Published private(set) var micPowerRatio = 0.0
    
    private let powerRatioEmissionsPerSecon = 20.0
    func startObservation() {
        do {
            let recordSetting: [String: Any] = [
                AVFormatIDKey: NSNumber(value: kAudioFormatAppleLossless),
                AVNumberOfChannelsKey: 1
            ]
            let recorder = try AVAudioRecorder(url: URL(fileURLWithPath: "/dev/null",isDirectory: true), settings: recordSetting)
            recorder.isMeteringEnabled = true
            recorder.record()
            self.audioRecorder  = recorder
            
            self.cancellable = Timer.publish(
                every: 1.0 / powerRatioEmissionsPerSecon,
                tolerance: 1.0 / powerRatioEmissionsPerSecon,
                on: .main,
                in: .common
            )
            .autoconnect()
            .sink { [weak self] _ in
                recorder.updateMeters()
                
                let powerOffset = recorder.averagePower(forChannel: 0)
                if powerOffset < -50 {
                    self?.micPowerRatio = 0.0
                }else {
                    let normalizedOffset = CGFloat( 50 + powerOffset )
                    self?.micPowerRatio = normalizedOffset
                }
            }
        } catch	{
            print("An error occurred when observing microphone power : \(error.localizedDescription)")
        }
    }
    
    func release() {
        cancellable = nil
        
        audioRecorder?.stop()
        audioRecorder = nil
        
        micPowerRatio = 0.0
    }
}
