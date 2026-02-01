package project.util.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import project.Repository.dao.UserDao;
import project.util.Secure;
import project.util.SecureIndescriminate;
import project.util.SecurityLevel;
import project.util.TokenUtil;

@Aspect
@Component
@SuppressWarnings("unused")
public class Security {

    UserDao dao;
    TokenUtil tokenUtil;

    @Autowired
    Security(TokenUtil tokenUtil, UserDao dao)
    {
        this.tokenUtil = tokenUtil;
        this.dao = dao;
    }

    @Pointcut("@annotation(security)")
    private void indescriminateMethod(SecureIndescriminate security) {}

    @Pointcut("@annotation(security)")
    private void secureMethod(Secure security) {}

    @Pointcut("@within(security)")
    private void indescriminateClass(SecureIndescriminate security) {}

    @Pointcut("@within(security)")
    private void secureClass(SecureIndescriminate security) {}

    @Pointcut("within(project..*)")
    private void withinProject() {}

    @Pointcut("within(test.*)")
    private void withinTest() {}

    @Pointcut("execution(org.springframework.http.ResponseEntity project..*(..))")
    private void returnsResponse() {}

    @Before("indescriminateMethod() && withinTest()")
    public void sayHello()
    {
        System.out.println("Hello world");
    }

    @Around("indescriminateClass(security) && returnsResponse() && args(authHeader,..)")
    public ResponseEntity<?> checkISecureClass(ProceedingJoinPoint joinPoint, String authHeader, SecureIndescriminate security)
        throws Throwable
    {
        return checkSecure(joinPoint, authHeader, security);
    }
    @Around("indescriminateMethod(security) && returnsResponse() && args(authHeader,..)")
    public ResponseEntity<?> checkISecureMethod(ProceedingJoinPoint joinPoint, String authHeader, SecureIndescriminate security)
        throws Throwable
    {
        return checkSecure(joinPoint, authHeader, security);
    }

    @Around("secureClass(security) && returnsResponse() && args(authHeader,id,..)")
    public ResponseEntity<?> checkSecureClass(ProceedingJoinPoint joinPoint, String authHeader, int id, Secure security)
        throws Throwable
    {
        return checkSecure(joinPoint, authHeader, id, security);
    }
    @Around("secureMethod(security) && returnsResponse() && args(authHeader,id,..)")
    public ResponseEntity<?> checkSecureMethod(ProceedingJoinPoint joinPoint, String authHeader, int id, Secure security)
        throws Throwable
    {
        return checkSecure(joinPoint, authHeader, id, security);
    }
    
    public ResponseEntity<?> checkSecure(ProceedingJoinPoint joinPoint, String authHeader, int user_id, Secure security)
        throws Throwable
    {
        var token = tokenUtil.asToken(authHeader);
        if (token.isValid())
        {
            if(!token.isExpired() && token.getId() == user_id) 
            {
                //verify user type
                int id = token.getId();
                var usr = dao.findById(id);
                SecurityLevel level = security.value();
                if (usr.isPresent() && usr.get().getRole().value >= level.value)
                {
                    //if the function throws an exception we dont want to intercept it
                    return (ResponseEntity<?>)joinPoint.proceed();
                }
            }
        }

        return ResponseEntity.status(403).build();
    }
    public ResponseEntity<?> checkSecure(ProceedingJoinPoint joinPoint, String authHeader, SecureIndescriminate security)
        throws Throwable
    {
        var token = tokenUtil.asToken(authHeader);
        /*
        System.out.printf("got auth header: %s\n", authHeader);
        System.out.printf("security level: %s\n", security.value());

        System.out.printf("claims: %s\n", token.getToken().toString());*/
        if (token.isValid())
        {
            if(!token.isExpired())
            {
                //verify user type
                int id = token.getId();
                var usr = dao.findById(id);
                SecurityLevel level = security.value();
                if (usr.isPresent() && (usr.get().getRole().value >= level.value))
                {
                    //if the function throws an exception we dont want to intercept it
                    return (ResponseEntity<?>)joinPoint.proceed();
                }
            }
        }

        return ResponseEntity.status(403).build();
    }
}
