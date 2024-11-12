package com.onlybuns.isa.repository;

import com.onlybuns.isa.model.Location;
import com.onlybuns.isa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LocationRepository extends JpaRepository<Location, Long> {
    public Location findById(long id);

}