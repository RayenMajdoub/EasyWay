//
//  LREdgeCutShapeView.swift
//  Easyway
//
//  Created by Rayen Majdoub on 16/4/2023.
//

import SwiftUI


struct LREdgeCutShapeView: Shape {
    func path(in rect: CGRect) -> Path {
        return Path { path in
            path.addArc(center: CGPoint(x: rect.minX, y: rect.midY),
                        radius: 12.0,
                        startAngle: Angle(degrees: 90),
                        endAngle: Angle(degrees: 270),
                        clockwise: true)
            path.addArc(center: CGPoint(x: rect.maxX, y: rect.midY),
                        radius: 12.0,
                        startAngle: Angle(degrees: 270),
                        endAngle: Angle(degrees: 90),
                        clockwise: true)
        }
    }
}
