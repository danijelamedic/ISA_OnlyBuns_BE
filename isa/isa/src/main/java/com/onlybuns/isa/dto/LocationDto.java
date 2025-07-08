package com.onlybuns.isa.dto;

import com.onlybuns.isa.model.Location;

public class LocationDto {
    private Long id;
    private double latitude;
    private double longitude;
    private String address;

    public LocationDto() {}

    public LocationDto(Location location) {
        this.id = location.getId();
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
        this.address = location.getAddress();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}