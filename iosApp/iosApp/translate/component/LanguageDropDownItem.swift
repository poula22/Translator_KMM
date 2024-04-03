//
//  LanguageDropDownItem.swift
//  iosApp
//
//  Created by poula george on 07/02/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

struct LanguageDropDownItem: View {
    var uiLanguage: UiLanguage
    var onClick: ()->Void
    var body: some View {
        Button(action: {
            onClick()
        }){
            if let image = UIImage(named:uiLanguage.imageName.lowercased()){
                HStack{
                    Text(uiLanguage.language.langName)
                        .foregroundColor(.onSurface)
                        
                    Image(uiImage:  image)
                        .resizable()
                        .frame(width:40,height: 40)
                        .padding(.trailing,5)
                }
            }
        }
    }
}

#Preview {
    LanguageDropDownItem(
        uiLanguage: UiLanguage(language: Language.german, imageName: "german"),
        onClick: {}
    )
}
