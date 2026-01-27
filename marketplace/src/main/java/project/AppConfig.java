package project;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import project.properties.MediaProperties;
<<<<<<< HEAD

@SpringBootApplication
@EnableConfigurationProperties(MediaProperties.class)
=======
import project.util.TokenUtil;

@SpringBootApplication
@EnableConfigurationProperties({MediaProperties.class, TokenUtil.TokenProperties.class})
>>>>>>> fb293b6a16fdaebce9a67603b34f6b957c358c4e
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
