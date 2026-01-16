package project.util.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import project.util.Secure;

@Aspect
@Component
@SuppressWarnings("unused")
public class Security {
    @Pointcut("@annotation(security)")
    private void secureMethod(Secure security) {}

    @Pointcut("@within(security)")
    private void secureClass(Secure security) {}

    @Pointcut("within(project..*)")
    private void withinProject() {}

    @Pointcut("within(test.*)")
    private void withinTest() {}

    @Before("secureMethods() && withinTest()")
    public void sayHello()
    {
        System.out.println("Hello world");
    }

    @Around("secureClass(security) && withinProject() && args(authHeader,..)")
    public Object checkSecureClass(ProceedingJoinPoint joinPoint, String authHeader, Secure security)
    {
        return checkSecureMethod(joinPoint, authHeader, security);
    }

    @Around("secureMethod(security) && withinProject() && args(authHeader,..)")
    public Object checkSecureMethod(ProceedingJoinPoint joinPoint, String authHeader, Secure security)
    {
        System.out.printf("got auth header: %s\n", authHeader);
        System.out.printf("security level: %s\n", security.value());

        if (authHeader.equals("abc"))
        {
            try 
            {
                return joinPoint.proceed();
            }
            catch (Throwable e)
            {

            }
        }

        return ResponseEntity.status(409).build();
    }
}
