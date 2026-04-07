package com.example.leets7th.global.aspect;

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

    @Before("execution(* com.example.leets7th.domain..*.*(..))")
    public void enableSoftDeleteFilter() {
        Session session = entityManager.unwrap(Session.class);
        session.enableFilter("deletedFilter");

    }


    @Around("@annotation(com.example.leets7th.global.annotation.DisableSoftDelete)")
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
