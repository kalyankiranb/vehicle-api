package com.zigatta.controller;

import com.zigatta.model.Vehicle;
import com.zigatta.service.VehicleService;
import com.zigatta.util.LoggingUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@Slf4j
@RunWith(PowerMockRunner.class)
public class VehicleControllerTest {

  private VehicleController vehicleController;
  @Mock
  private VehicleService vehicleService;
  @Mock
  private LoggingUtils loggingUtils;
  private static final String VALID_TRANSMISSION_TYPE = "AUTO";
  private static final String INVALID_TRANSMISSION_TYPE = "INVALID";
  private static final String VEHICLE_ID = "64758";
  private MockHttpServletRequest request;

  @Before
  public void setup() {
    initMocks(this);
    vehicleController = new VehicleController(vehicleService, loggingUtils);
    request = new MockHttpServletRequest();
  }

  @Test
  public void createVehicleSuccessTest() {
    Vehicle vehicle = createVehicleRequest(VEHICLE_ID, VALID_TRANSMISSION_TYPE);
    when(vehicleService.createVehicle(any(Vehicle.class))).thenReturn(vehicle);
    RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    ResponseEntity responseEntity = vehicleController.createVehicle(vehicle);
    Assert.assertEquals("Status code 201 is expected", 201, responseEntity.getStatusCodeValue());
  }

  @Test
  public void createVehicleInvalidTransmissiontypeTest() {
    Vehicle vehicle = createVehicleRequest(VEHICLE_ID, INVALID_TRANSMISSION_TYPE);
    doThrow(TransactionSystemException.class).when(vehicleService).createVehicle(any(Vehicle.class));
    ResponseEntity responseEntity = vehicleController.createVehicle(vehicle);
    Assert.assertEquals("Status code 400 is expected", 400, responseEntity.getStatusCodeValue());
  }

  @Test
  public void createVehicleValidTransmissionServerErrorTest() {
    Vehicle vehicle = createVehicleRequest(VEHICLE_ID, VALID_TRANSMISSION_TYPE);
    vehicle.setVehicleId(null);
    when(vehicleService.createVehicle(any(Vehicle.class))).thenReturn(vehicle);
    ResponseEntity responseEntity = vehicleController.createVehicle(vehicle);
    Assert.assertEquals("Status code 500 is expected", 500, responseEntity.getStatusCodeValue());
  }

  public Vehicle createVehicleRequest(String vehicleId, String transmissionType) {
    Vehicle vehicle = new Vehicle();
    vehicle.setVehicleId(vehicleId);
    vehicle.setVin("1234");
    vehicle.setVehicleYear("2001");
    vehicle.setModel("model1");
    vehicle.setMake("make1");
    vehicle.setTransmissionType(transmissionType);
    return vehicle;
  }
}
