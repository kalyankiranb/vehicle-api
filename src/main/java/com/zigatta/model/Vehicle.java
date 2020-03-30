package com.zigatta.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Pattern;

@Entity
public class Vehicle {
  @Id
  private String vehicleId;
  private String vin;
  private String vehicleYear;
  private String make;
  private String model;
  @Pattern(regexp = "^(MANUAL|AUTO)")
  private String transmissionType;

  public Vehicle() {

  }

  public Vehicle(String vehicleId,
                 String vin,
                 String vehicleYear,
                 String make,
                 String model,
                 String transmissionType) {
    this.vehicleId = vehicleId;
    this.vin = vin;
    this.vehicleYear = vehicleYear;
    this.make = make;
    this.model = model;
    this.transmissionType = transmissionType;
  }

  public String getVehicleId() {
    return vehicleId;
  }

  public void setVehicleId(String vehicleId) {
    this.vehicleId = vehicleId;
  }

  public String getVin() {
    return vin;
  }

  public void setVin(String vin) {
    this.vin = vin;
  }

  public String getVehicleYear() {
    return vehicleYear;
  }

  public void setVehicleYear(String vehicleYear) {
    this.vehicleYear = vehicleYear;
  }

  public String getMake() {
    return make;
  }

  public void setMake(String make) {
    this.make = make;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public String getTransmissionType() {
    return transmissionType;
  }

  public void setTransmissionType(String transmissionType) {
    this.transmissionType = transmissionType;
  }
}

enum TransmissionType {
  MANUAL,
  AUTO;
}