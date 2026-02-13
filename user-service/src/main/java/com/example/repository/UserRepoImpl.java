package com.example.repository;

import com.example.model.User;
import com.example.util.HibernateUtil;
import lombok.extern.log4j.Log4j2;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Optional;


@Log4j2
public class UserRepoImpl implements UserRepo {

    @Override
    public User createUser(User user) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
            log.info("User created: {}", user);
            return user;
        } catch (HibernateException | NullPointerException e) {
            if (transaction.isActive()) {
                try {
                    transaction.rollback();
                } catch (IllegalStateException rollbackEx) {
                    log.error("Ошибка при откате транзакции: {}", rollbackEx.getMessage(), rollbackEx);
                }
            }
            log.error("Ошибка базы данных при создании пользователя: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to create user", e);
        } catch (Exception e) {
            log.error("Неожиданная ошибка при создании пользователя: {}", e.getMessage(), e);
            throw new RuntimeException("Unexpected error", e);
        }

    }

    @Override
    public User updateUser(User newUser) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            newUser = session.merge(newUser);
            transaction.commit();
            log.info("Пользователь обновлен: {}", newUser);
            return newUser;
        } catch (HibernateException | NullPointerException e) {
            if (transaction.isActive()) {
                try {
                    transaction.rollback();
                } catch (IllegalStateException rollbackEx) {
                    log.error("Ошибка при откате транзакции: {}", rollbackEx.getMessage(), rollbackEx);
                }
            }
            log.error("Ошибка базы данных при обновлении пользователя: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to update user", e);
        } catch (Exception e) {
            log.error("Неожиданная ошибка при обновлении пользователя: {}", e.getMessage(), e);
            throw new RuntimeException("Unexpected error", e);
        }
    }

    @Override
    public void deleteUser(User user) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.remove(session.merge(user));
            transaction.commit();
            log.info("Пользователь удален.");
        } catch (HibernateException | NullPointerException e) {
            if (transaction.isActive()) {
                try {
                    transaction.rollback();
                } catch (IllegalStateException rollbackEx) {
                    log.error("Ошибка при откате транзакции: {}", rollbackEx.getMessage(), rollbackEx);
                }
            }
            log.error("Ошибка базы данных при удалении пользователя: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to delete user", e);
        } catch (Exception e) {
            log.error("Неожиданная ошибка при удалении пользователя: {}", e.getMessage(), e);
            throw new RuntimeException("Unexpected error", e);
        }

    }

    @Override
    public Optional<User> getUserById(Long userId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Optional<User> user = Optional.ofNullable(session.find(User.class, userId));
            transaction.commit();
            log.info("Пользователь удален.");
            return user;
        } catch (HibernateException | NullPointerException e) {
            if (transaction.isActive()) {
                try {
                    transaction.rollback();
                } catch (IllegalStateException rollbackEx) {
                    log.error("Ошибка при откате транзакции: {}", rollbackEx.getMessage(), rollbackEx);
                }
            }
            log.error("Ошибка базы данных при удалении пользователя: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to delete user", e);
        } catch (Exception e) {
            log.error("Неожиданная ошибка при удалении пользователя: {}", e.getMessage(), e);
            throw new RuntimeException("Unexpected error", e);
        }
    }
}
