package com.example.repository;

import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import static com.example.util.HibernateUtil.sessionFactory;

public class HibernateUtil {
    public static void buildSessionFactory(
            String driver, String url, String username, String password,
            String dialect, String hbm2ddl) {

        if (sessionFactory != null) {
            shutdown();
        }

        try {
            Configuration configuration = new Configuration();

            configuration.setProperty("hibernate.connection.driver_class", driver);
            configuration.setProperty("hibernate.connection.url", url);
            configuration.setProperty("hibernate.connection.username", username);
            configuration.setProperty("hibernate.connection.password", password);
            configuration.setProperty("hibernate.dialect", dialect);
            configuration.setProperty("hibernate.hbm2ddl.auto", hbm2ddl);
            configuration.setProperty("hibernate.show_sql", "true");
            configuration.setProperty("hibernate.format_sql", "true");

            configuration.addAnnotatedClass(com.example.model.entity.User.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .build();

            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
            sessionFactory = null;
        }
    }
}
