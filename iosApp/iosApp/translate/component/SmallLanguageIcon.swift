//
//  SmallLanguageIcon.swift
//  iosApp
//
//  Created by poula george on 19/02/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

struct SmallLanguageIcon: View {
    var language: UiLanguage
    var body: some View {
        Image(uiImage: UIImage(named:language.imageName.lowercased())!)
            .resizable()
            .frame(width: 35,height: 35)
    }
}
#Preview {
    SmallLanguageIcon(language: UiLanguage(language: Language.german, imageName: "german"))
}
