package project.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
/**
 * @Secure is an annotation that verifies the first string as a jwt.
 * if the jwt is invalid then responds with status 409
 * otherwise executes the method as normal 
 * 
 * The first string must be a String bound to the Authorization header of the http request
 */
public @interface SecureIndescriminate {

    SecurityLevel value();
}
