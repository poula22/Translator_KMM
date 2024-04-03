//
//  LanguageDropDown.swift
//  iosApp
//
//  Created by poula george on 19/02/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

struct LanguageDropDown: View {
    var uiLanguage: UiLanguage
    var isOpen: Bool
    var selectLanguage: (UiLanguage) -> Void
    var body: some View {
        Menu{
            VStack {
                ForEach(UiLanguage.Companion().allLanguages, id: \.self.language.langCode) { uiLanguage in
                    LanguageDropDownItem(
                        uiLanguage: uiLanguage,
                        onClick: {
                            selectLanguage(uiLanguage)
                        }
                    )
                }
            }
        } label: {
            HStack{
                SmallLanguageIcon(language: uiLanguage)
                Text(uiLanguage.language.langName)
                    .foregroundColor(.lightBlue)
                Image(systemName: isOpen ? "chevron.up" : "chevron.down")
                
            }
        }
    }
}

#Preview {
    LanguageDropDown(
        uiLanguage: UiLanguage(language: Language.german, imageName: "german"),
        isOpen: true,
        selectLanguage: {uiLanguage in }
    )
}
