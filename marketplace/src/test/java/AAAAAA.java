import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import test.AppContextTest;
import test.TestAppConfig;

public class AAAAAA {
    public static void main(String[] args)
    {
        ApplicationContext ctx = SpringApplication.run(TestAppConfig.class);
        var bean = ctx.getBean(AppContextTest.class);
    }
}
