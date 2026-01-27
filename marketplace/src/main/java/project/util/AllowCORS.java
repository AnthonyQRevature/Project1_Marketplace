package project.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.web.bind.annotation.CrossOrigin;

//composite annotation
/**
 * basically just shorthand for \@CrossOrigin with the origins property filled in
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@CrossOrigin(origins="http://localhost:5173")
public @interface AllowCORS {

}
