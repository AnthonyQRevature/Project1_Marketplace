package project;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext appContext = new AnnotationConfigApplicationContext(AppConfig.class);

        HelloWorldBean helloWorld = appContext.getBean(HelloWorldBean.class);
        helloWorld.HelloWorld();
    }
}