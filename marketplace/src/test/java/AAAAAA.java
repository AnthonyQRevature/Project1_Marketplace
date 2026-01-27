import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import project.Repository.dao.UserDao;
import test.TestAppConfig;

public class AAAAAA {
    public static void main(String[] args)
    {
        ApplicationContext ctx = SpringApplication.run(TestAppConfig.class);
        var bean = ctx.getBean(UserDao.class);
        var list = bean.findUserByDistance(33.03810434840791, -96.69287478499572, 5);

        for (var e : list)
        {
            System.out.println(e);
        }
    }
}
