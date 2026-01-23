
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import project.AppConfig;
import project.HelloWorldBean;

//getting the context of the spring application from outside of the application
public class AppContextTest {

    public static void main(String[] args)
    {
        ApplicationContext ctx = SpringApplication.run(AppConfig.class);
        var bean = ctx.getBean(HelloWorldBean.class);
        bean.HelloWorld();
    }
}