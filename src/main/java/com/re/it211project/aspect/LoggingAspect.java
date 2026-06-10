package com.re.it211project.aspect;

import com.re.it211project.dto.request.JobApplicationRequest;
import com.re.it211project.dto.response.JobApplicationResponse;
import com.re.it211project.security.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Before("execution(* com.re.it211project.controller..*(..))")
    public void logBeforeController(JoinPoint joinPoint) {
        log.info("Request to method: {}", joinPoint.getSignature().getName());
    }

    @AfterReturning(
            pointcut = "execution(* com.re.it211project.service.JobApplicationService.applyJob(..))",
            returning = "result"
    )
    public void logApplyJobSuccess(JoinPoint joinPoint, Object result) {
        Object[] args = joinPoint.getArgs();

        if (args.length > 0 && args[0] instanceof JobApplicationRequest request) {
            Long candidateId = getCurrentUserId();

            log.info(
                    "Candidate ID: {} applied for Job ID: {} successfully",
                    candidateId,
                    request.getJobId()
            );
        }

        if (result instanceof JobApplicationResponse response) {
            log.info(
                    "Application ID: {} created with status: {}",
                    response.getId(),
                    response.getStatus()
            );
        }
    }

    @AfterThrowing(
            pointcut = "execution(* com.re.it211project.service..*(..))",
            throwing = "ex"
    )
    public void logServiceException(JoinPoint joinPoint, Throwable ex) {
        log.error(
                "Exception in method: {} with message: {}",
                joinPoint.getSignature().getName(),
                ex.getMessage()
        );
    }

    private Long getCurrentUserId() {
        try {
            Object principal = SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal();

            if (principal instanceof CustomUserDetails userDetails) {
                return userDetails.getId();
            }
        } catch (Exception ignored) {
        }

        return null;
    }
}