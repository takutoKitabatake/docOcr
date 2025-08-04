package tenant.common.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * AOP aspect for common checks across tenant operations.
 */
@Aspect
@Component
public class ComCheckAspect {

    private static final Logger logger = LoggerFactory.getLogger(ComCheckAspect.class);

    /**
     * Check method execution for controllers.
     */
    @Before("execution(* *..*Controller.*(..))")
    public void checkControllerExecution(JoinPoint joinPoint) {
        logger.debug("Executing controller method: {}.{}", 
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName());
        
        // Add common validation logic here if needed
        validateRequestParameters(joinPoint.getArgs());
    }

    /**
     * Check method execution for services.
     */
    @Before("execution(* *..*Service.*(..))")
    public void checkServiceExecution(JoinPoint joinPoint) {
        logger.debug("Executing service method: {}.{}", 
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName());
        
        // Add common business logic validation here if needed
    }

    /**
     * Validate common request parameters.
     */
    private void validateRequestParameters(Object[] args) {
        for (Object arg : args) {
            if (arg instanceof String) {
                String stringArg = (String) arg;
                if (stringArg != null && stringArg.trim().isEmpty()) {
                    logger.warn("Empty string parameter detected");
                }
            }
        }
    }
}