package com.onlybuns.isa.service;

import com.onlybuns.isa.model.Location;
import com.onlybuns.isa.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Cacheable(value = "locations", key = "#id")
    public Location findById(long id) {
        System.out.println(">>> FETCHING FROM DB for id = " + id);
        return locationRepository.findById(id).orElse(null);
    }

    @Cacheable(value = "locations", key = "#address.trim().toLowerCase()")
    public Location findByAddress(String address) {
        System.out.println(">>> FETCHING FROM DB for address = " + address);
        return locationRepository.findByAddress(address);
    }

    @CachePut(value = "locations", key = "#result.id")
    public Location save(Location location) {
        Location saved = locationRepository.save(location);
        System.out.println(">>> SAVED AND UPDATED CACHE for id = " + saved.getId());
        return saved;
    }

    // (Opcionalno) Brisanje keša za određenu lokaciju:
    @CacheEvict(value = "locations", key = "#id")
    public void evictFromCache(long id) {
        System.out.println(">>> EVICTED FROM CACHE id = " + id);
    }
}
