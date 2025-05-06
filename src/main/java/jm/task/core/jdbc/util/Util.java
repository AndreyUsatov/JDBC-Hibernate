package jm.task.core.jdbc.util;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Slf4j
public class Util {
    private static String driver;
    private static String host;
    private static String login;
    private static String password;



    static {
        try (InputStream input = Util.class.getClassLoader().getResourceAsStream("db.properties")) {
            Properties properties = new Properties();
            if (input == null) {
                log.info("Извините, не удалось найти db.properties");

            }
            properties.load(input);
            driver = properties.getProperty("db.driver");
            host = properties.getProperty("db.host");
            login = properties.getProperty("db.login");
            password = properties.getProperty("db.password");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(host, login, password);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
