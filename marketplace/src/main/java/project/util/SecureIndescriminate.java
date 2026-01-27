package project.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
/**
 * Checks the jwt token to verify if it is a valid token.
 * To use this annotation, add this to a method that matches the following pattern: <br/>
 * <code>public ResponseEntity{@literal<String>} method({@literal@RequestHeader}("Authorization") String authHeader,...)</code>
 * <br/>
 * see {@link project.controller.Echo}.
 */
public @interface SecureIndescriminate {

    SecurityLevel value() default SecurityLevel.USER;
}
