package jm.task.core.jdbc.dao;

public class UserQueries {
    public static final String CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS users (" +
            "id BIGSERIAL PRIMARY KEY," +
            "name VARCHAR(128) NOT NULL ," +
            "lastName VARCHAR(128) NOT NULL ," +
            "age SMALLINT NOT NULL )";

    public static final String DROP_USERS_TABLE = "DROP TABLE IF EXISTS users";

    public static final String TRUNCATE_USERS_TABLE = "TRUNCATE TABLE users";
}