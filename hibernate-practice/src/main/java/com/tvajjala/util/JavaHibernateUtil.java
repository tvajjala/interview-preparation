package com.tvajjala.util;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import com.jolbox.bonecp.BoneCPDataSource;
import com.mysql.jdbc.Connection;

/**
 * This doesn't requires configuration file
 *
 * @author ThirupathiReddy V
 *
 */
public class JavaHibernateUtil {

    private JavaHibernateUtil() {
    }

    private static SessionFactory sessionFactory;

    static Properties getProperties() {
        final Properties properties = new Properties();
        properties.put("hibernate.dialect", org.hibernate.dialect.MySQL5InnoDBDialect.class.getName());
        properties.put("hibernate.hbm2ddl.auto", "create-drop");
        properties.put("hibernate.show_sql", "true");
        // properties.put("hibernate.connection.autocommit", "false");
        // properties.put("connection.autocommit", "false");
        properties.put("hibernate.connection.datasource", dataSource());
        // ISOLATION LEVEL
        properties.put("hibernate.connection.isolation", Connection.TRANSACTION_READ_UNCOMMITTED);

        return properties;
    }

    static DataSource dataSource() {
        // instantiate, configure and return production DataSource
        final BoneCPDataSource dataSource = new BoneCPDataSource();
        dataSource.setDriverClass("com.mysql.jdbc.Driver");
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/test");
        dataSource.setIdleConnectionTestPeriodInMinutes(1);
        dataSource.setUsername("root");
        dataSource.setPassword("admin");
        dataSource.setCloseOpenStatements(true);
        dataSource.setIdleMaxAgeInMinutes(1);
        dataSource.setDefaultTransactionIsolation("SERIALIZABLE");// NOT WORKING
        dataSource.setMaxConnectionsPerPartition(100);
        dataSource.setMinConnectionsPerPartition(50);
        dataSource.setPartitionCount(1);
        dataSource.setAcquireIncrement(50);
        return dataSource;
    }

    static {
        final Configuration configuration = new Configuration();
        configuration.addProperties(getProperties());
        configuration.addAnnotatedClass(com.tvajjala.domain.User.class);
        configuration.addAnnotatedClass(com.tvajjala.domain.AutoIncrement.class);
        configuration.addAnnotatedClass(com.tvajjala.domain.IdentityBean.class);
        configuration.addAnnotatedClass(com.tvajjala.domain.SequenceGen.class);
        configuration.addAnnotatedClass(com.tvajjala.domain.VOStudent.class);
        configuration.addAnnotatedClass(com.tvajjala.domain.CCustomer.class);
        final StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        sessionFactory = configuration.buildSessionFactory(builder.build());
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
