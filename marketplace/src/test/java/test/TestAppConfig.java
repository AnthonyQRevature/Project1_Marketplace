package test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import project.AppConfig;

@SpringBootApplication
@ComponentScan(basePackageClasses={TestAppConfig.class, AppConfig.class})
public class TestAppConfig {
}
