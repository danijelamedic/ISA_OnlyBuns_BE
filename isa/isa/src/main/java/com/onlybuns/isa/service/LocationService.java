package com.onlybuns.isa.service;

import com.onlybuns.isa.model.Location;
import com.onlybuns.isa.model.User;
import com.onlybuns.isa.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationService {
    @Autowired
    private LocationRepository locationRepository;

    public Location findById(long id){
        return locationRepository.findById(id);
    }
}