package nl.hu.bep.billy;


import nl.hu.bep.billy.authentication.LoginManager;
import nl.hu.bep.billy.authentication.User;
import nl.hu.bep.billy.persistence.IStorageController;
import nl.hu.bep.billy.persistence.LocalFileStorageController;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@WebListener
public class Main implements ServletContextListener {

    public static IStorageController storageController = new LocalFileStorageController();
    private ScheduledExecutorService executorService;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        executorService = Executors.newSingleThreadScheduledExecutor();
        LoginManager loginManager = new LoginManager();

        List<User> users = storageController.load();
        if (users.isEmpty()) {
            loginManager.populate();
        } else {
            for (User user : users) {
                loginManager.addUser(user);
            }
            System.out.println("Loaded " + users.size() + " users");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        LoginManager loginManager = new LoginManager();
        System.out.println("Closing");
        storageController.save(loginManager.getUsers());
        if (executorService != null) {
            executorService.shutdownNow();
        }
    }
}
