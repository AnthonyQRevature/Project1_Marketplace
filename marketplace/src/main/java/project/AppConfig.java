package project;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class AppConfig {

    @Bean
    public HelloWorldBean helloWorldBean()
    {
        return new HelloWorldBean();
    }

    /*
    @Bean
    public Hasher hasher()
    {
        return new Hasher();
    }
    */

    /*
    @Bean
    public DateUtil dateUtil() {return new DateUtil();}
    */
}
