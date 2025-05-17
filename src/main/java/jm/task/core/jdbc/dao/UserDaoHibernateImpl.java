package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@NoArgsConstructor
@Slf4j
public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory = Util.getConnection();




    @Override
     public void createUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery(UserQueries.CREATE_USERS_TABLE).executeUpdate();
            transaction.commit();
            log.info("Таблица создана");
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Ошибка при создании таблицы: " + e.getMessage(), e);
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery(UserQueries.DROP_USERS_TABLE).executeUpdate();
            transaction.commit();
            log.info("Таблица удалена");
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Ошибка при удалении таблицы: " + e.getMessage(), e);
        }
    }

    @Override
    public void saveUser (String name, String lastName, byte age) {
        if (sessionFactory == null) {
            throw new IllegalStateException("SessionFactory is not initialized.");
        }
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.save(new User(name, lastName, age));
                transaction.commit();
                log.info("User  с именем – " + name + " добавлен в базу данных");
            } catch (HibernateException e) {
                transaction.rollback();
                                throw new RuntimeException("Ошибка при сохранении пользователя: " + e.getMessage(), e);
            } catch (Exception e) {
                               throw new RuntimeException("Ошибка при работе с сессией: " + e.getMessage(), e);
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        if (sessionFactory == null) {
            throw new IllegalStateException("SessionFactory is not initialized.");
        }

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                User user = session.get(User.class, id);
                if (user != null) {
                    session.delete(user);
                    transaction.commit();
                    log.info("User  с ID " + id + " удален");
                } else {
                    log.warn("Пользователь с ID " + id + " не найден");
                }
            } catch (HibernateException e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                throw new RuntimeException("Ошибка при удалении пользователя: " + e.getMessage(), e);
            }
        } catch (Exception e) {
                        throw new RuntimeException("Ошибка при работе с сессией: " + e.getMessage(), e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        if (sessionFactory == null) {
            throw new IllegalStateException("SessionFactory is not initialized.");
        }

        try (Session session = sessionFactory.openSession()) {
            CriteriaQuery<User> criteriaQuery = session.getCriteriaBuilder().createQuery(User.class);
            criteriaQuery.from(User.class);

            List<User> userList = session.createQuery(criteriaQuery).getResultList();
            return userList;
        } catch (HibernateException e) {
                       throw new RuntimeException("Ошибка при получении списка пользователей: " + e.getMessage(), e);
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
                log.info("Таблица очищена");
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