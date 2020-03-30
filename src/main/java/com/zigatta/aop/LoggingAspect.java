package com.zigatta.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
@Slf4j
public class LoggingAspect {

  @Around("@annotation(com.zigatta.aop.Logging)")
  public void logAspect(ProceedingJoinPoint joinPoint) throws Throwable {
    log.info("Entering method: " + joinPoint.toShortString());
    joinPoint.proceed();
    log.info("Exiting method: " + joinPoint.toShortString());
  }
}
