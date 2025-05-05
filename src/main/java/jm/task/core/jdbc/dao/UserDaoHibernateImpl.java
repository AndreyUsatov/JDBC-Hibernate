package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory = Util.getConnection();

    public UserDaoHibernateImpl() {

    }

    public class UserQueries {
        public static final String CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS users (" +
                "id BIGSERIAL PRIMARY KEY," +
                "name VARCHAR(128) NOT NULL ," +
                "lastName VARCHAR(128) NOT NULL ," +
                "age SMALLINT NOT NULL )";

        public static final String DROP_USERS_TABLE = "DROP TABLE IF EXISTS users";

        public static final String TRUNCATE_USERS_TABLE = "TRUNCATE TABLE users";
    }

    @Override
    public void createUsersTable() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.createNativeQuery(UserQueries.CREATE_USERS_TABLE).executeUpdate();
            transaction.commit();
            System.out.println("Таблица создана");
        } catch (HibernateException e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Ошибка при создании таблицы: " + e.getMessage(), e);
        } finally {
            session.close();
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.createNativeQuery(UserQueries.DROP_USERS_TABLE).executeUpdate();
            transaction.commit();
            System.out.println("Таблица удалена");
        } catch (HibernateException e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Ошибка при удалении таблицы: " + e.getMessage(), e);
        } finally {
            session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        if (sessionFactory == null) {
            throw new IllegalStateException("SessionFactory is not initialized.");
        }
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.save(new User(name, lastName, age));
                transaction.commit();
                System.out.println("User  с именем – " + name + " добавлен в базу данных");
            } catch (HibernateException e) {
                if (transaction != null) {
                    transaction.rollback();
                    throw new RuntimeException("Ошибка при сохранении пользователя: " + e.getMessage(), e);
                }
            } catch (Exception e) {
                throw new RuntimeException("Ошибка при работе с сессией: " + e.getMessage(), e);
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.delete(session.get(User.class, id));
            transaction.commit();
            System.out.println("User удален");
        } catch (HibernateException e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Ошибка при удалении пользователя: " + e.getMessage(), e);
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        Session session = sessionFactory.openSession();
        CriteriaQuery<User> criteriaQuery = session.getCriteriaBuilder().createQuery(User.class);
        criteriaQuery.from(User.class);
        Transaction transaction = session.beginTransaction();
        List<User> userList = session.createQuery(criteriaQuery).getResultList();
        try {
            transaction.commit();
            return userList;
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Ошибка при получении списка пользователей: " + e.getMessage(), e);
        } finally {
            session.close();
        }
    }


    @Override
    public void cleanUsersTable() {
        if (sessionFactory == null) {
            throw new IllegalStateException("SessionFactory is not initialized.");
        }

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.createNativeQuery(UserQueries.TRUNCATE_USERS_TABLE).executeUpdate();
                transaction.commit();
                System.out.println("Таблица очищена");
            } catch (HibernateException e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                throw new RuntimeException("Ошибка при очистке таблицы пользователей: " + e.getMessage(), e);
            }
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при открытии сессии: " + e.getMessage(), e);
        }
    }
}