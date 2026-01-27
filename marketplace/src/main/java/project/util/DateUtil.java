package project.util;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.stereotype.Component;

/*
 * @Component:
 * Spring detects this as a bean without us having to define a @Bean definition manually
 */
@Component
public class DateUtil {
    public Date currentDate()
    { 
        return Date.valueOf(LocalDate.now()); 
    }
}
