package com.parking.parkinglot.common;

import com.parking.parkinglot.entities.User;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link com.parking.parkinglot.entities.Car}
 */
public class CarDto {
    Integer id;
    String licensePlate;
    String parkingSpot;
    String ownerName;

    public CarDto(String ownerName, String licensePlate, Integer id, String parkingSpot) {
        this.ownerName = ownerName;
        this.licensePlate = licensePlate;
        this.id = id;
        this.parkingSpot = parkingSpot;
  }

    public Integer getId() {
        return id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getParkingSpot() {
        return parkingSpot;
    }

    public String getOwnerName() {
        return ownerName;
    }
}