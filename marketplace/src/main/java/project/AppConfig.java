package project;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import project.properties.MediaProperties;

@SpringBootApplication
@EnableConfigurationProperties(MediaProperties.class)
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
}
