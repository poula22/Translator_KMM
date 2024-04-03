//
//  LanguageDisplay.swift
//  iosApp
//
//  Created by poula george on 27/03/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

struct LanguageDisplay: View {
    var language: UiLanguage
    
    var body: some View {
        HStack {
            SmallLanguageIcon(language: language)
            Text(language.language.langName)
                .foregroundColor(.lightBlue)
        }
    }
}

#Preview {
    LanguageDisplay(language: UiLanguage(language: Language.german, imageName: "german"))
}
