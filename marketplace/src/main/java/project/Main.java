package project;

import javax.sql.DataSource;

import org.hibernate.HibernateException;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.boot.SpringApplication;

public class Main {
    public static void main(String[] args) {
        try
        {
            var context = SpringApplication.run(AppConfig.class, args);
        }
        catch (BeanCreationException e)
        {
            var c = e.getCause();
            if (c instanceof ServiceException)
            {
                var c2 = c.getCause();
                if (c2 instanceof HibernateException)
                {
                    c.printStackTrace();
                    System.out.println("failed to connect to Database");
                    return;
                }
            }
            throw e;
        }
    }
}