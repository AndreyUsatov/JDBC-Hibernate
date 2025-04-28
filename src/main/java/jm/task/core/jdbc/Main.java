package jm.task.core.jdbc;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    private static final UserService userService = new UserServiceImpl();
    private static final User userDao1 = new User("Виктор", "Перваков", (byte) 60);
    private static final User userDao2 = new User("Владислав", "Саюшкин", (byte) 32);
    private static final User userDao3 = new User("Илья", "Серебренников", (byte) 35);
    private static final User userDao4 = new User("Алеся", "Касаткина", (byte) 33);

    public static void main(String[] args) {

        userService.createUsersTable();
        userService.saveUser(userDao1.getName(), userDao1.getLastName(), userDao1.getAge());
        userService.saveUser(userDao2.getName(), userDao2.getLastName(), userDao2.getAge());
        userService.saveUser(userDao3.getName(), userDao3.getLastName(), userDao3.getAge());
        userService.saveUser(userDao4.getName(), userDao4.getLastName(), userDao4.getAge());
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();

    }
}
