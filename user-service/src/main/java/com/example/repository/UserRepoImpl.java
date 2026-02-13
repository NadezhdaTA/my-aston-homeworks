package com.example.repository;

import com.example.model.entity.User;
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
            log.info("Пользователь создан: {}", user);
            return user;
        } catch (HibernateException e) {
            rollbackTransaction(transaction);
            log.error("Ошибка базы данных при создании пользователя: {}", e.getMessage(), e);
            throw new IllegalArgumentException("Failed to create user", e);
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
            User existingUser = session.get(User.class, newUser.getId());

            if (newUser.getUserName() != null) existingUser.setUserName(newUser.getUserName());
            if (newUser.getEmail() != null) existingUser.setEmail(newUser.getEmail());
            if (newUser.getAge() != null) existingUser.setAge(newUser.getAge());

            transaction.commit();
            log.info("Пользователь обновлен: {}", newUser);
            return newUser;
        } catch (HibernateException e) {
            rollbackTransaction(transaction);
            log.error("Ошибка базы данных при обновлении пользователя: {}", e.getMessage(), e);
            throw new IllegalArgumentException("Failed to update user", e);
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
            log.info("Пользователь удален: {}", user);
        } catch (HibernateException e) {
            rollbackTransaction(transaction);
            log.error("Ошибка базы данных при удалении пользователя: {}", e.getMessage(), e);
            throw new IllegalArgumentException("Failed to delete user", e);
        } catch (Exception e) {
            log.error("Неожиданная ошибка при удалении пользователя: {}", e.getMessage(), e);
            throw new RuntimeException("Unexpected error", e);
        }

    }

    @Override
    public Optional<User> getUserById(Long userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Optional<User> user = Optional.ofNullable(session.find(User.class, userId));
            log.info("Пользователь найден: {}", user);
            return user;
        } catch (HibernateException e) {
            log.error("Ошибка базы данных при удалении пользователя: {}", e.getMessage(), e);
            throw new IllegalArgumentException("Failed to delete user", e);
        } catch (Exception e) {
            log.error("Неожиданная ошибка при удалении пользователя: {}", e.getMessage(), e);
            throw new RuntimeException("Unexpected error", e);
        }
    }

    private void rollbackTransaction(Transaction transaction) {
        if (transaction != null && transaction.isActive()) {
            try {
                transaction.rollback();
            } catch (IllegalStateException e) {
                log.error("Ошибка при откате транзакции: {}", e.getMessage(), e);
            }
        }
    }
}
