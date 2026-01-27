package project.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
/**
 * Checks the jwt token to verify that it matches the user id passed into the function.
 * To use this annotation, add this to a method that matches the following pattern: <br/>
 * <code>public ResponseEntity{@literal<String>} method({@literal@RequestHeader}("Authorization") String authHeader, {@literal@PathVariable("user_id")} int user_id,...)</code>
 * <br/>
 * see {@link project.controller.Echo}.
 */
public @interface Secure {
    SecurityLevel value() default SecurityLevel.USER;
}
