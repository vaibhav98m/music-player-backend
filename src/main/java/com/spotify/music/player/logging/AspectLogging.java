package com.spotify.music.player.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Aspect
@Component
@Log4j2
public class AspectLogging {

//	execution(modifiers-pattern? return-type-pattern declaring-type-pattern? method-name-pattern(param-pattern) throws-pattern?)
//	execution(): Matches method executions.
//	modifiers-pattern?: (Optional) Access modifiers like public or protected.
//	return-type-pattern: Specifies the return type (* matches all types).
//	declaring-type-pattern?: (Optional) Specifies the package/class.
//	method-name-pattern: Specifies the method name.
//	param-pattern: Specifies the method parameters (.. matches any number of parameters).
//	throws-pattern?: (Optional) Specifies the exceptions thrown.

	// Pointcut to match all methods in a package
	@Pointcut("execution(* com.spotify.music.player..*(..)) && !@annotation(com.spotify.music.player.annotation.SkipLogging)")
	public void serviceMethods() {
	}

	// Before advice
	@Before("serviceMethods()")
	public void logBefore() {
		log.info("Method Execution Started");
	}

	// After advice
	@After("serviceMethods()")
	public void logAfter() {
		log.info("Method Execution Finished");
	}

	// Around advice
	@Around("serviceMethods()")
	public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
		log.info("Method {} started with arguments {}", joinPoint.getSignature(), joinPoint.getArgs());
		Object result = joinPoint.proceed();
		log.info("Method {} finished", joinPoint.getSignature());
		return result;
	}

}
