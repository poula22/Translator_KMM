//
//  IosVoiceToTextViewModel.swift
//  iosApp
//
//  Created by poula george on 04/04/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared
import Combine

@MainActor class IosVoiceToTextViewModel: ObservableObject {
    private var parser: any VoiceToTextParser
    private let langCode: String
    
    private let viewModel: VoiceToTextViewModel
    @Published var state = UiVoiceToTextState(
        powerRatios: [],
        spokenText: "",
        canRecord: false,
        recordError: nil,
        displayState: nil
    )
    private var handle: DisposableHandle?
    
    init(
        parser: VoiceToTextParser,
        langCode: String
    ) {
        self.parser = parser
        self.langCode = langCode
        self.viewModel = VoiceToTextViewModel(parser: parser, coroutineScope: nil)
    }
    
    func onEvent(event: VoiceToTextEvent) {
        viewModel.onEvent(event: event)
    }
    
    func startObserving() {
        handle = viewModel.state.subscribe {
            [weak self] state in
            if let state {
                self?.state = state
            }
        }
    }
    func dispose() {
        handle?.dispose()
        onEvent(event: .Reset())
    }
}
