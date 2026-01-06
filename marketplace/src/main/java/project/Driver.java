package project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import project.Repository.dao.UserDao;

//Component exists simply to prove that it connects to the database
@Component
public class Driver implements CommandLineRunner {

    @Autowired
    UserDao dao;

    @Override
    public void run(String... args) throws Exception {
        var entities = dao.findAll();
        System.out.printf("==========\nTable \'users\' has %d entities \n==========\n", entities.size());
    }
}
