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
    private static final String driver;
    private static final String host;
    private static final String login;
    private static final String password;



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
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Ошибка при загрузке db.properties: " + e.getMessage(), e);
        }
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(host, login, password);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Ошибка при получении соединения с базой данных: " + e.getMessage(), e);
        }
        return connection;
    }
}
