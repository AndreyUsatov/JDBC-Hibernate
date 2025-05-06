package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;


import java.sql.SQLException;

@Slf4j
public class Main {
    public static void main(String[] args) throws SQLException {
        // реализуйте алгоритм здесь
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        log.info("");
        userService.saveUser("Виктор", "Перваков", (byte) 60);
        userService.saveUser("Владислав", "Саюшкин", (byte) 32);
        userService.saveUser("Илья", "Серебренников", (byte) 35);
        userService.saveUser("Алеся", "Касаткина", (byte) 33);
        log.info("");
        userService.getAllUsers();
        log.info("");
        userService.removeUserById(1);
        log.info("");
        userService.cleanUsersTable();
        log.info("");
        userService.dropUsersTable();
    }
}

