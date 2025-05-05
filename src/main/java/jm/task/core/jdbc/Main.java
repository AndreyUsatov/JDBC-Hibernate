package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        // реализуйте алгоритм здесь
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Виктор", "Перваков", (byte) 60);
        userService.saveUser("Владислав", "Саюшкин", (byte) 32);
        userService.saveUser("Илья", "Серебренников", (byte) 35);
        userService.saveUser("Алеся", "Касаткина", (byte) 33);

        userService.getAllUsers();
        userService.removeUserById(1);
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}

