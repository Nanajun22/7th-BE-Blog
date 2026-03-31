package com.example.demo.aspect;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class SoftDeleteAspect {
    private final EntityManager entityManager;

    @Before("execution(* com.example.demo.service..*.*(..))")
    public void enableSoftDeleteFilter() {
        Session session = entityManager.unwrap(Session.class);
        session.enableFilter("deletedFilter");

    }


    @Around("@annotation(com.example.demo.annotation.DisableSoftDelete)")
    public Object disableSoftDeleteFilter(ProceedingJoinPoint joinPoint) throws Throwable {
        Session session = entityManager.unwrap(Session.class);
        session.disableFilter("deletedFilter");


        try{
            return joinPoint.proceed();
        }

        finally {
            session.enableFilter("deletedFilter");
        }
    }


}
