package com.ullash.springaop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class AopAspect {
    Logger logger = LoggerFactory.getLogger(AopAspect.class);


    @Before("execution(* AopClass+.*(..))")
    public void before(JoinPoint joinPoint){
        logger.info("*** starting - "+joinPoint.getSignature().getName());
        logger.info("**** With parameter - " + Arrays.toString(joinPoint.getArgs()));
    }

//    @After("execution(* AopClass+.*(..))")
//	public void after(JoinPoint joinPoint) {
//		logger.info("**** Finished - " + joinPoint.getSignature().getName());
//	}


    @Pointcut("execution(* AopClass+.*(..))")
    public void getAfterPointCut(){
    }


    @AfterReturning(pointcut = "getAfterPointCut()", returning="result")
    public void after(JoinPoint joinPoint , Object result){
        logger.info("**** Finished - " + joinPoint.getSignature().getName());
        logger.info("Returned  -"+result);
    }



    @Pointcut("execution(* User+.*(..))")
    public void userServiceMethods() {
    }

    @AfterThrowing(pointcut = "userServiceMethods()", throwing = "ex")
    public void handleException(JoinPoint joinPoint, Throwable ex) {
        System.out.println("Exception occurred in method: " + joinPoint.getSignature().getName());
        System.out.println("Exception message: " + ex.getMessage());
    }


    @Around("execution(* CustomUserService+.*(..))")
    public Object logMethodExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        // Proceed with the method invocation
        Object result = joinPoint.proceed();

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime + 1000;

        System.out.println("Method " + joinPoint.getSignature().getName() + " execution time: " + executionTime + " milliseconds");

        return result;
    }



}
