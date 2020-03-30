package com.zigatta.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoggingUtils {
  private final ObjectMapper objectMapper;

  @Autowired
  public LoggingUtils(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  public String getJSONLoggingString(final Object o) {
    try {
      return objectMapper.writeValueAsString(o);
    } catch (JsonProcessingException e) {
      return String.format("Instance of class %s failed to JSONify with error: %s", o.getClass().getCanonicalName(), e.getMessage());
    }
  }

}
