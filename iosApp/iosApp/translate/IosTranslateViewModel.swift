//
//  IosTranslateViewModel.swift
//  iosApp
//
//  Created by poula george on 20/02/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared

extension TranslateScreen{
    @MainActor class IOSTranslateViewModel: ObservableObject{
        private var historyDataSource: HistoryDataSource
        private var translateUseCase: Translate
        
        private let viewModel: TranslateViewModel
        
        @Published var state: TranslateState = TranslateState(
            fromText: "",
            toText: nil,
            isTranslating: false,
            fromLanguage: UiLanguage(language: .english, imageName: "english"),
            toLanguage: UiLanguage(language: .german, imageName: "german"),
            isChoosingFromLanguage: false,
            isChoosingToLanguage: false,
            error: nil,
            history: []
        )
        
        init(historyDataSource: HistoryDataSource, translateUseCase: Translate) {
            self.historyDataSource = historyDataSource
            self.translateUseCase = translateUseCase
            self.viewModel = TranslateViewModel(
                translateWebService: translateUseCase,
                historyDataSource: historyDataSource,
                coroutineScope: nil
            )
        }
        private var handle: DisposableHandle?

        
        func startObserving(){
            handle = viewModel.state.subscribe(onCollect: { state in
                if let state = state{
                    self.state = state
                }
            })
        }
        
        func onEvent(event:TranslateEvent) {
            self.viewModel.onEvent(event: event)
        }
        
        func dispose(){
            handle?.dispose()
        }
    }
}
