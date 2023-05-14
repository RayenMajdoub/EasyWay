//
//  LineShape.swift
//  Easyway
//
//  Created by Rayen Majdoub on 16/4/2023.
//

import SwiftUI

struct LineShape: Shape {
    func path(in rect: CGRect) -> Path {
        return Path { path in
            path.move(to: CGPoint(x: 0, y: 0))
            path.addLine(to: CGPoint(x: rect.width, y: 0))
        }
    }
}
