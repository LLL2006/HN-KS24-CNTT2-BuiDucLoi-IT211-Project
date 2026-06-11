package com.re.it211project.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Around("execution(* com.re.it211project.service..*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        try {
            Object result = joinPoint.proceed();

            long end = System.currentTimeMillis();

            log.info(
                    "Phương thức {} được thực thi trong {} ms",
                    joinPoint.getSignature().toShortString(),
                    end - start
            );

            return result;
        } catch (Throwable ex) {
            long end = System.currentTimeMillis();

            log.error(
                    "Phương thức {} thực thi thất bại sau {} ms. Lỗi: {}",
                    joinPoint.getSignature().toShortString(),
                    end - start,
                    ex.getMessage()
            );

            throw ex;
        }
    }
}