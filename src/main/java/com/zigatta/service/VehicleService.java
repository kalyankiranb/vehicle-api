package com.zigatta.service;

import com.zigatta.model.Vehicle;
import com.zigatta.repository.VehicleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;
import java.util.UUID;

@Service
@Slf4j
public class VehicleService {

  @Autowired
  private VehicleRepository vehicleRepository;

  public Vehicle createVehicle(Vehicle vehicle) {
    try {
      vehicle.setVehicleId(UUID.randomUUID().toString());
      vehicleRepository.save(vehicle);
    } catch (TransactionSystemException ex) {
      log.error(ex.getMessage());
      throw ex;
    }
    return vehicle;
  }
}
