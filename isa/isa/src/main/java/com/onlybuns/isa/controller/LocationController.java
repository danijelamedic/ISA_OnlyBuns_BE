package com.onlybuns.isa.controller;

import com.onlybuns.isa.model.Location;
import com.onlybuns.isa.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @PostMapping
    public ResponseEntity<Location> createLocation(@RequestBody Location location) {
        Location existing = locationService.findByAddress(location.getAddress());
        // Proveravam da li postoji lokacija sa tom adresom ( aktivira L2 kes)

        if (existing != null) {
            // Ako postoji, vrati postojecu lokaciju iz kesa ili baze
            return new ResponseEntity<>(existing, HttpStatus.OK);
        }

        Location savedLocation = locationService.save(location);
        return new ResponseEntity<>(savedLocation, HttpStatus.CREATED);
    }
}
