package project;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AppConfig {

    @Bean
    public HelloWorldBean helloWorldBean()
    {
        return new HelloWorldBean();
    }
}
