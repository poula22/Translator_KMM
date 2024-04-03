//
//  TranslateHistoryItem.swift
//  iosApp
//
//  Created by poula george on 02/04/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

struct TranslateHistoryItem: View {
    let item: UiHistoryItem
    let onClick: () -> Void
    
    var body: some View {
        Button(action: /*@START_MENU_TOKEN@*/{}/*@END_MENU_TOKEN@*/ ){
            VStack(alignment:.leading){
                HStack{
                    SmallLanguageIcon(language: item.fromLanguage)
                        .padding(.trailing)
                    Text(item.fromText)
                        .foregroundStyle(Color.lightBlue)
                        .font(.body)
                }.padding(.bottom)
                
                HStack{
                    SmallLanguageIcon(language: item.toLanguage)
                        .padding(.trailing)
                    Text(item.toText)
                        .foregroundStyle(Color.lightBlue)
                        .font(.body)
                }
            }
            .padding()
            .frame(maxWidth:.infinity,alignment: .leading)
            .gradientSurface()
            .cornerRadius(15)
            .shadow(radius: 4)
        }
    }
}

#Preview {
    TranslateHistoryItem(
        item: UiHistoryItem(
            id: 0,
            fromText: "hello",
            toText: "مرحبا",
            fromLanguage: UiLanguage(language: Language.english, imageName: "english"),
            toLanguage: UiLanguage(language: Language.arabic, imageName: "arabic")
        ),
        onClick: {}
    )
}
