package com.zigatta.controller;

import com.zigatta.aop.Logging;
import com.zigatta.model.Vehicle;
import com.zigatta.service.VehicleService;
import com.zigatta.util.LoggingUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/vehicle-api/v1")
@Slf4j
public class VehicleController {

  private VehicleService vehicleService;
  private LoggingUtils loggingUtils;

  @Autowired
  public VehicleController(VehicleService vehicleService,
                           LoggingUtils loggingUtils) {
    this.vehicleService = vehicleService;
    this.loggingUtils = loggingUtils;
  }

  @PostMapping(value = "/vehicles/vehicle", consumes = "application/json")
  @Logging
  public ResponseEntity createVehicle(@RequestBody Vehicle vehicle) {
    log.info(String.format("Received vehicle request body: %s", loggingUtils.getJSONLoggingString(vehicle)));
    URI location;
    try {
      vehicle = vehicleService.createVehicle(vehicle);
    } catch (TransactionSystemException ex) {
      log.info("Validation Failed for TransmissionType");
      final Map<String, String> responseBody = new HashMap<>();
      responseBody.put("Message", "Validation failed for TransmissionType value. Should be either MANUAL or AUTO");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }

    if(vehicle.getVehicleId() !=  null) {
      log.info(String.format("Created vehicle: %s", vehicle));
      location = ServletUriComponentsBuilder.fromCurrentRequest()
                      .path("/{vehicleId}")
                      .buildAndExpand(vehicle.getVehicleId())
                      .toUri();
      return ResponseEntity.created(location).build();
    } else {
      log.warn("Vehicle creation failed");
      final Map<String, String> responseBody = new HashMap<>();
      responseBody.put("Message", "Internal Server ERROR. Please try again after sometime.");
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
    }
  }

}
