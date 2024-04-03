//
//  TranslateScreen.swift
//  iosApp
//
//  Created by poula george on 20/02/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

struct TranslateScreen: View {
    private var historyDataSource: HistoryDataSource
    private var translateUseCase: Translate
    @ObservedObject var viewModel: IOSTranslateViewModel
    
    init(historyDataSource: HistoryDataSource, translateUseCase: Translate) {
        self.historyDataSource = historyDataSource
        self.translateUseCase = translateUseCase
        self.viewModel = IOSTranslateViewModel(
            historyDataSource: historyDataSource,
            translateUseCase: translateUseCase
        )
    }
    
    var body: some View {
        ZStack {
            List {
                HStack(alignment:.center){
                    LanguageDropDown(
                        uiLanguage: viewModel.state.fromLanguage,
                        isOpen: viewModel.state.isChoosingFromLanguage) { language in
                            viewModel.onEvent(event: TranslateEvent.ChooseFromLanguage(language: language))
                        }
                    Spacer()
                    SwapLanguageButton {
                        viewModel.onEvent(event: TranslateEvent.SwapLanguages())
                    }
                    Spacer()
                    LanguageDropDown(
                        uiLanguage: viewModel.state.toLanguage,
                        isOpen: viewModel.state.isChoosingToLanguage) { language in
                            viewModel.onEvent(event: TranslateEvent.ChooseToLanguage(language: language))
                        }
                    
                }
                .listRowSeparator(.hidden)
                .listRowBackground(Color.background)
                
                TranslateTextField(
                    fromText: Binding(
                        get: { viewModel.state.fromText },
                        set: {value in
                            viewModel.onEvent(event: .ChangeTranslationText(text: value))
                        }
                    ),
                    toText: viewModel.state.toText,
                    fromLanguage: viewModel.state.fromLanguage,
                    toLanguage: viewModel.state.toLanguage,
                    isTraslating: viewModel.state.isTranslating,
                    onTranslatingEvent: { viewModel.onEvent(event: $0) }
                )
                .listRowSeparator(.hidden)
                .listRowBackground(Color.background)
                
                if !viewModel.state.history.isEmpty {
                    Text("History")
                        .font(/*@START_MENU_TOKEN@*/.title/*@END_MENU_TOKEN@*/)
                        .bold()
                        .frame(maxWidth:.infinity,alignment: .leading)
                        .listRowSeparator(.hidden)
                        .listRowBackground(Color.background)
                    
                }
                ForEach(viewModel.state.history, id: \.self.id) { item in
                    TranslateHistoryItem(
                        item: item,
                        onClick: {
                            viewModel.onEvent(event: .SelectHistoryItem(item: item))
                        }
                    )
                    .listRowSeparator(.hidden)
                    .listRowBackground(Color.background)
                    
                }
            }
            .listStyle(.plain)
            .buttonStyle(.plain)
            
            VStack{
                Spacer()
                NavigationLink(destination: Text("Voice-To-text")) {
                    ZStack {
                        Circle()
                            .foregroundStyle(Color.primaryColor)
                            .padding()
                        Image(uiImage: UIImage(named: "mic")!)
                            .foregroundStyle(Color.onPrimary)
                    }.frame(
                        maxWidth: 100,
                        maxHeight: 100
                    )
                }
            }
            
        }
        .onAppear{
            viewModel.startObserving()
        }
        .onDisappear{
            viewModel.dispose()
        }
    }
}
