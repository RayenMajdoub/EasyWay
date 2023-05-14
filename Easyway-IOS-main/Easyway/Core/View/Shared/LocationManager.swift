//
//  LocationManager.swift
//  Easyway
//
//  Created by Rayen Majdoub on 25/4/2023.
//
import MapKit
import CoreLocation
class LocationManager: NSObject,CLLocationManagerDelegate, ObservableObject {
    @Published var region = MKCoordinateRegion()
    private let manager = CLLocationManager()
    @Published var userlocation : CLLocationCoordinate2D?
    
    override init() {
            super.init()
            manager.delegate = self
            manager.desiredAccuracy = kCLLocationAccuracyBest
            manager.requestWhenInUseAuthorization()
            manager.startUpdatingLocation()
        }
    func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
        guard !locations.isEmpty else {return }
        userlocation = locations.first?.coordinate
        manager.stopUpdatingLocation()
        }
}
