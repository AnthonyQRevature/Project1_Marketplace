package test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import project.Repository.dao.PostDao;
import project.util.Secure;
import project.util.SecurityLevel;

@Component
public class AppContextTest implements CommandLineRunner {

    @Autowired
    PostDao dao;

    @Override
    @Transactional
    @Secure(SecurityLevel.GUEST)
    public void run(String... args) throws Exception {
        getAll();
    }

    public void getAll()
    {
        var allPosts = dao.findAll();
        for (var p : allPosts)
        {
            System.out.println(p.toString());
            for (var m : p.media)
            {
                System.out.println(m.toString());
            }
        }
    }
    
}