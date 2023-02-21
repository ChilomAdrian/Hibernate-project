package org.example.utils;

import lombok.extern.java.Log;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

@Log
public class HibernateUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private HibernateUtil() {
        throw new IllegalStateException("Utility class");
    }

    private static SessionFactory buildSessionFactory() {
        try {
            // Create the ServiceRegistry from hibernate.cfg.xml
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()//
                    .configure("hibernate.cfg.xml").build();

            // Create a metadata sources using the specified service registry.
            Metadata metadata = new MetadataSources(serviceRegistry).getMetadataBuilder().build();

            return metadata.getSessionFactoryBuilder().build();
        } catch (Exception ex) {

            log.severe(String.format("%sInitial SessionFactory creation failed.", ex));
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}