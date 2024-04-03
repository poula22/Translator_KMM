//
//  Color.swift
//  iosApp
//
//  Created by poula george on 06/02/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import shared

extension Color{
    init(hex:Int64,alpha:Double = 1){
        self.init(
            .sRGB,
            red: Double((hex >> 16) & 0xff) / 255,
            green: Double((hex >> 08) & 0xff)/255,
            blue: Double((hex >> 00) & 0xff)/255,
            opacity: alpha
        )
    }
    private static let colors = AppColors()
    static let lightBlue = Color(hex: colors.LightBlue)
    static let LightBlueGrey = Color(hex: colors.LightBlueGrey)
    static let AccentViolet = Color(hex: colors.AccentViolet)
    static let DarkGrey = Color(hex: colors.DarkGrey)
    static let textBlack = Color(hex: colors.TextBlack)
    
    static let primaryColor = Color(light: .AccentViolet, dark: .AccentViolet)
    static let background = Color(light: .LightBlueGrey, dark: .DarkGrey)
    static let onPrimary = Color(light: .white, dark: .white)
    static let onBackground = Color(light: .textBlack, dark: .white)
    static let surface = Color(light: .white, dark: .DarkGrey)
    static let onSurface = Color(light: .textBlack, dark: .white)
}

private extension Color{
    init(light:Self,dark:Self){
        self.init(
            uiColor: UIColor(light: UIColor(light), dark: UIColor(dark))
        )
    }
}
private extension UIColor{
    convenience init(light:UIColor,dark:UIColor) {
        self.init{ traits in
            switch traits.userInterfaceStyle{
            case .light , .unspecified:
                return light
            case .dark:
                return dark
            @unknown default:
                return light
            }
        }
    }
}
