//
//  TranslateTextField.swift
//  iosApp
//
//  Created by poula george on 01/04/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared
import UniformTypeIdentifiers

struct TranslateTextField: View {
    @Binding var fromText: String
    let toText: String?
    let fromLanguage: UiLanguage
    let toLanguage: UiLanguage
    let isTraslating: Bool
    let onTranslatingEvent: (TranslateEvent) -> Void
    
    var body: some View {
        if toText == nil || isTraslating {
            IdleTextField(
                fromText: $fromText,
                fromLanguage: fromLanguage,
                isTraslating: isTraslating,
                onTranslatingEvent: onTranslatingEvent
            )
            .gradientSurface()
            .cornerRadius(15)
            .animation(.easeInOut, value: isTraslating)
            .shadow(radius: 4)
        
            
        } else {
          TranslatedTextField(
            fromText: fromText,
            toText: toText ?? "" ,
            fromLanguage: fromLanguage,
            toLanguage: toLanguage,
            isTraslating: isTraslating,
            onTranslatingEvent: onTranslatingEvent
          )
          .padding()
          .gradientSurface()
          .cornerRadius(15)
          .animation(.easeOut, value: isTraslating)
          .shadow(radius: 4)
          .onTapGesture {
              onTranslatingEvent(.EditTranslation())
          }
        }
    }
}

#Preview {
    TranslateTextField(
        fromText: Binding(
            get: { "test" },
            set: { value in }
            ),
        toText: nil,
        fromLanguage: UiLanguage(language: .english, imageName: "english"),
        toLanguage: UiLanguage(language: .arabic, imageName: "arabic"),
        isTraslating: false,
        onTranslatingEvent: { event in
            
            
        })
}

private extension TranslateTextField {
    struct IdleTextField: View {
        @Binding var fromText: String
        let fromLanguage: UiLanguage
        let isTraslating: Bool
        let onTranslatingEvent: (TranslateEvent) -> Void
        
        var body: some View {
            TextEditor(text: $fromText)
                .frame(
                    maxWidth: .infinity,
                    minHeight: 200,
                    alignment: .topLeading
                )
                .padding()
                .foregroundStyle(Color.onSurface)
                .scrollContentBackground(.hidden)
                .overlay(alignment: .bottomTrailing) {
                    ProgressButton(
                        text: "translate",
                         isLoading: isTraslating,
                        onClick: {
                            onTranslatingEvent(TranslateEvent.Translate())
                        }
                    )
                    .padding(.trailing)
                    .padding(.bottom)
                }
//                .onAppear {
//                    UITextView.appearance().backgroundColor = .clear
//                }
        }
    }
    
    struct TranslatedTextField: View {
        let fromText: String
        let toText: String
        let fromLanguage: UiLanguage
        let toLanguage: UiLanguage
        let isTraslating: Bool
        let onTranslatingEvent: (TranslateEvent) -> Void
        let tts = TextToSpeech()
        
        var body: some View {
            VStack(alignment: .leading) {
                LanguageDisplay(language: fromLanguage)
                Text(fromText)
                    .foregroundColor(.onSurface)
                HStack {
                    Spacer()
                    Button(action: {
                        UIPasteboard.general.setValue(
                            fromText,
                            forPasteboardType: UTType.plainText.identifier
                        )
                    }){
                        Image(uiImage: UIImage(named: "copy")!)
                            .renderingMode(/*@START_MENU_TOKEN@*/.template/*@END_MENU_TOKEN@*/)
                            .foregroundColor(.lightBlue)
                    }
                    
                    Button(action: {
                        onTranslatingEvent(.CloseTranslation())
                    }){
                        Image(systemName: "xmark")
                            .renderingMode(/*@START_MENU_TOKEN@*/.template/*@END_MENU_TOKEN@*/)
                            .foregroundColor(.lightBlue)
                    }
                }
                Divider().padding()
                LanguageDisplay(language: toLanguage)
                    .padding(.bottom)
                Text(toText)
                    .foregroundColor(.onSurface)
                HStack {
                    Spacer()
                    Button(action: {
                        UIPasteboard.general.setValue(
                            toText,
                            forPasteboardType: UTType.plainText.identifier
                        )
                    }){
                        Image(uiImage: UIImage(named: "copy")!)
                            .renderingMode(/*@START_MENU_TOKEN@*/.template/*@END_MENU_TOKEN@*/)
                            .foregroundColor(.lightBlue)
                    }
                    
                    Button(action: {
                        tts.speak(
                            text: toText,
                            language: toLanguage.language.langCode
                        )
                    }){
                        Image(systemName: "speaker.wave.2")
                            .renderingMode(/*@START_MENU_TOKEN@*/.template/*@END_MENU_TOKEN@*/)
                            .foregroundColor(.lightBlue)
                    }
                }
            }
        }
    }
}
