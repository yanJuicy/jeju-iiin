package com.example.jejuiiin.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Aspect
public class TimeAop {

	@Around("execution(public * com.example.jejuiiin.controller..*(..))")
	public synchronized Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
		// 측정 시작 시간
		long startTime = System.currentTimeMillis();

		try {
			// 핵심기능 수행
			Object output = joinPoint.proceed();
			return output;
		} finally {
			// 측정 종료 시간
			long endTime = System.currentTimeMillis();
			// 수행시간 = 종료 시간 - 시작 시간
			long runTime = endTime - startTime;

			log.info("[API Use Time] :" + joinPoint.getSignature().getName() + " " + runTime + "ms");
		}
	}

}
