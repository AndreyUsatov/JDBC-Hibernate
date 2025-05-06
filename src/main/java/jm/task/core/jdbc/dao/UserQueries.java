package jm.task.core.jdbc.dao;

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

