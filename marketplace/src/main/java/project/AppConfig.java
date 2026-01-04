package project;

import org.springframework.context.annotation.Bean;

public class AppConfig {

    @Bean
    public HelloWorldBean helloWorldBean()
    {
        return new HelloWorldBean();
    }
}
