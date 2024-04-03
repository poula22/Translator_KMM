//
//  ProgressButton.swift
//  iosApp
//
//  Created by poula george on 27/03/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

struct ProgressButton: View {
    var text: String
    var isLoading:Bool
    var onClick:()->Void
    
    var body: some View {
        Button(action: {
            if !isLoading {
                onClick()
            }
        }){
            if isLoading {
                ProgressView()
                    .animation(.easeIn, value: isLoading)
                    .padding(8	)
                    .background(Color.primaryColor)
                    .foregroundColor(Color.onPrimary)
                    .cornerRadius(100)
                    .progressViewStyle(CircularProgressViewStyle(tint: .white))
            }else {
                Text(text.uppercased())
                    .animation(.easeIn, value: isLoading)
                    .padding(.horizontal)
                    .padding(.vertical,5)
                    .background(Color.primaryColor)
                    .foregroundColor(Color.onPrimary)
                    .cornerRadius(100)
            }
        }
    }
}

#Preview {
    ProgressButton(
        text: "Translate",
        isLoading: false	,
        onClick: {}
    )
}
