package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;




@Slf4j
@NoArgsConstructor
public class UserDaoJDBCImpl implements UserDao {



    public void createUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(UserQueries.CREATE_TABLE);
            log.info("Таблица создана");
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось создать таблицу", e);
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(UserQueries.DROP_TABLE);
            log.info("Таблица удалена");
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось удалить таблицу", e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UserQueries.INSERT_USER)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            log.info("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось добавить пользователя:", e);
        }
    }


    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement(UserQueries.DELETE_USER_BY_ID)) {
            statement.setLong(1, id);
            statement.executeUpdate();
            log.info("User удален");
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось удалить пользователя", e);
        }
    }

    public List<User> getAllUsers() {
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
        } catch (SQLException e) {
            log.error("Ошибка при получении всех пользователей: {}", e.getMessage());
            throw new RuntimeException("Не удалось получить список пользователей", e);
        }
        return allUsers;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(UserQueries.CLEAN_TABLE);
            log.info("Таблица очищена");
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось очистить таблицу пользователей", e);
        }
    }
}
