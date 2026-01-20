import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;

import javax.imageio.ImageIO;

import project.Repository.dao.UserDao;
import test.TestAppConfig;

public class AAAAAA {
    public static void main(String[] args)
    {
        ApplicationContext ctx = SpringApplication.run(TestAppConfig.class);
        var bean = ctx.getBean(UserDao.class);
        var list = bean.findUserByDistance(150, 250);

        for (var e : list)
        {
            System.out.println(e);
        }
    }
}
