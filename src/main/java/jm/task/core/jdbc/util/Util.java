
package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class Util {
    private static SessionFactory sessionFactory = null;

    public static SessionFactory getConnection() {
        if (sessionFactory == null) {
            Properties properties = new Properties();
            try (InputStream input = new FileInputStream("src/main/resources/db.properties")) {
                properties.load(input);

                Configuration configuration = new Configuration()
                        .setProperty("hibernate.connection.driver_class", properties.getProperty("db.driver"))
                        .setProperty("hibernate.connection.url", properties.getProperty("db.host"))
                        .setProperty("hibernate.connection.username", properties.getProperty("db.login"))
                        .setProperty("hibernate.connection.password", properties.getProperty("db.password"))
                        .setProperty("hibernate.dialect", properties.getProperty("hibernate.dialect"))
                        .addAnnotatedClass(User.class)
                        .setProperty("hibernate.c3p0.min_size", properties.getProperty("hibernate.c3p0.min_size"))
                        .setProperty("hibernate.c3p0.max_size", properties.getProperty("hibernate.c3p0.max_size"))
                        .setProperty("hibernate.c3p0.max_statements", properties.getProperty("hibernate.c3p0.max_statements"));

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (IOException e) {
                throw new RuntimeException("Ошибка при загрузке файла свойств: " + e.getMessage(), e);
            } catch (HibernateException e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}