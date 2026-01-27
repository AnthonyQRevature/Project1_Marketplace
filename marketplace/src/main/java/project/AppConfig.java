package project;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import project.properties.MediaProperties;
import project.util.TokenUtil;

@SpringBootApplication
@EnableConfigurationProperties({MediaProperties.class, TokenUtil.TokenProperties.class})
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
