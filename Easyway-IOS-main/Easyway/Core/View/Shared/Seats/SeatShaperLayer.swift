//
//  SeatShaperLayer.swift
//  Easyway
//
//  Created by Rayen Majdoub on 17/4/2023.
//

import SwiftUI

/// Seat shape layer
class SeatShapeLayer: CAShapeLayer {
    
    override init(layer: Any) {
        super.init(layer: layer)
    }
    
    fileprivate var size: CGSize!
    
    init(frame: CGRect) {
        super.init()
        self.frame = frame
        size = frame.size
        setup()
    }
    
    override init() {
        super.init()
        setup()
    }
    
    /// Setup
    func setup() {
        strokeColor = UIColor.lightGray.cgColor
        fillColor = UIColor.clear.cgColor
        
        let drawPath = CGMutablePath()
        drawPath.addRoundedRect(in: CGRect(x: 0, y: 0, width: size.width, height: size.height), cornerWidth: 4, cornerHeight: 4, transform: .identity)
        path = drawPath
    }
    
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
    }
    
    /// Selected
    func selected() {
            let textLayer = CATextLayer()
            textLayer.frame = CGRect(x: 0, y: 0, width: size.width, height: size.height)
            textLayer.alignmentMode = .center
            textLayer.fontSize = 15
            textLayer.cornerRadius = 4
            textLayer.backgroundColor = UIColor.lightGray.cgColor
            textLayer.foregroundColor = UIColor.white.cgColor
            addSublayer(textLayer)
    }
}
