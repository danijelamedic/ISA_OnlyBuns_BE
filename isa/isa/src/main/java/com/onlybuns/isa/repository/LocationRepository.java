package com.onlybuns.isa.repository;

import com.onlybuns.isa.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {
    Optional<Location> findById(long id);
    public Location findByAddress(String address);
}
