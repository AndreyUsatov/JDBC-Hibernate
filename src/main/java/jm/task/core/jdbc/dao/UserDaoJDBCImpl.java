package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Slf4j
public class UserDaoJDBCImpl implements UserDao {

    private static final Logger logger = LoggerFactory.getLogger(UserDaoJDBCImpl.class);

    public UserDaoJDBCImpl() {

    }

    public class UserQueries {
        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS users" +
                "(id SERIAL PRIMARY KEY," +
                " name VARCHAR(50), " +
                "lastname VARCHAR(50), " +
                "age SMALLINT)";

        public static final String DROP_TABLE = "DROP TABLE IF EXISTS users";

        public static final String INSERT_USER = "INSERT INTO users (name, lastname, age) VALUES (?, ?, ?)";

        public static final String DELETE_USER_BY_ID = "DELETE FROM users WHERE id = ?";

        public static final String SELECT_ALL_USERS = "SELECT * FROM users";

        public static final String CLEAN_TABLE = "DELETE FROM users";
    }

    public void createUsersTable() throws SQLException {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(UserQueries.CREATE_TABLE);
            logger.info("Таблица создана");
        }
    }

    public void dropUsersTable() throws SQLException {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(UserQueries.DROP_TABLE);
            logger.info("Таблица удалена");
        }
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UserQueries.INSERT_USER)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            logger.info("User с именем – " + name + " добавлен в базу данных");
        }
    }

    public void removeUserById(long id) throws SQLException {
        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement(UserQueries.DELETE_USER_BY_ID)) {
            statement.setLong(1, id);
            statement.executeUpdate();
            logger.info("User удален");
        }
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> allUsers = new ArrayList<>();
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(UserQueries.SELECT_ALL_USERS);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));
                allUsers.add(user);
            }
        }
        return allUsers;
    }

    public void cleanUsersTable() throws SQLException {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(UserQueries.CLEAN_TABLE);
            logger.info("Таблица очищена");
        }
    }
}
