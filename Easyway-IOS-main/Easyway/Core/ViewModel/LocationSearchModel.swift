//
//  LocationSearchModel.swift
//  Easyway
//
//  Created by Rayen Majdoub on 26/4/2023.
//

import Foundation

import MapKit

class LocationSearchModel:NSObject,ObservableObject
{
    @Published var selectedLocation:String?
    @Published var selectedLocationCoordinate: CLLocationCoordinate2D?
    @Published var results = [MKLocalSearchCompletion]()
    let manager = LocationManager()
    private let searchCompleter = MKLocalSearchCompleter()
    var queryFragment:String = "" {
        didSet {
            searchCompleter.queryFragment = queryFragment
            
        }
    }
    override init() {
        super.init()
        searchCompleter.delegate = self
        searchCompleter.queryFragment = queryFragment
    }
    func selectLocation(_ location:MKLocalSearchCompletion)
    {
        self.selectedLocation = location.title
        print(self.selectedLocation)
        locationSearch(forLocationSearchCompletion: location){
            response,error in
            if let error = error {
                print("Error in SearchModel ; \(error.localizedDescription)")
                return
            }
            guard let item = response?.mapItems.first else {return}
            let coordinate = item.placemark.coordinate
            self.selectedLocationCoordinate = coordinate
            print("coordinates : \(coordinate)")

        }   }
    func locationSearch(forLocationSearchCompletion localSearch:MKLocalSearchCompletion,completion:@escaping MKLocalSearch.CompletionHandler)
    {
        let searchRequest = MKLocalSearch.Request()
        searchRequest.naturalLanguageQuery = localSearch.title.appending(localSearch.subtitle)
        let search = MKLocalSearch(request: searchRequest)
        
        search.start(completionHandler: completion)
    }

    func userlocationTitle()->String{
        var title :String = ""
        let location = CLLocation(latitude:manager.userlocation!.latitude, longitude:manager.userlocation!.longitude)
        CLGeocoder().reverseGeocodeLocation(location) { placemarks, error in
            guard let placemark = placemarks?.first else {
                print("No location found.")
                return
            }
            
             title = placemark.name ?? ""
   
            print("Location title: \(title)")
        }
        return title
    }
    

}
extension LocationSearchModel : MKLocalSearchCompleterDelegate
{
    func completerDidUpdateResults(_ completer: MKLocalSearchCompleter) {
        self.results = completer.results
    }
}
