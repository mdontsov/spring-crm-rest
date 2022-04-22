package demo.rest.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.hibernate.SessionFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Logger;

@Configuration
@EnableWebMvc // <- similar to <mvc:annotation-driven/>
@EnableTransactionManagement
@ComponentScan("demo.rest") // <- NEVER include 'src/main/java' in components scanning
@PropertySource("classpath:database.properties") // <- jdbc connection properties from file
public class DemoAppConfig implements WebMvcConfigurer {

    @Autowired
    private Environment environment; // <- will hold the data from properties file

    private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    @Bean
    public DataSource dataSource() {
        // create connection pool
        ComboPooledDataSource pooledDataSource = new ComboPooledDataSource();
        try {
            // set the jdbc driver
            pooledDataSource.setDriverClass(environment.getProperty("jdbc.driver")); // <- read config from properties
        } catch (PropertyVetoException pve) {
            logger.severe(pve.getMessage());
        }
        // log the connection properties
        logger.info(">>> jdbc.url: ".concat(Objects.requireNonNull(environment.getProperty("jdbc.url"))));
        logger.info(">>> jdbc.user: ".concat(Objects.requireNonNull(environment.getProperty("jdbc.user"))));

        // set DB connection properties
        pooledDataSource.setJdbcUrl(environment.getProperty("jdbc.url"));
        pooledDataSource.setUser(environment.getProperty("jdbc.user"));
        pooledDataSource.setPassword(environment.getProperty("jdbc.password"));

        // set connection pool properties
        pooledDataSource.setInitialPoolSize(
                getIntProperty(environment.getProperty("connection.pool.initialPoolSize")));

        pooledDataSource.setMinPoolSize(
                getIntProperty(environment.getProperty("connection.pool.minPoolSize")));

        pooledDataSource.setMaxPoolSize(
                getIntProperty(environment.getProperty("connection.pool.maxPoolSize")));

        pooledDataSource.setMaxIdleTime(
                getIntProperty(environment.getProperty("connection.pool.maxIdleTime")));

        return pooledDataSource;
    }

    private @NotNull Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", environment.getProperty("hibernate.dialect"));
        properties.setProperty("hibernate.show_sql", environment.getProperty("hibernate.show_sql"));
        return properties;
    }

    // read environment property and convert it to int
    private int getIntProperty(String propertyName) {
        return Integer.parseInt(propertyName);
    }

    // SessionFactory bean
    @Bean
    public LocalSessionFactoryBean factory() {
        // create session factory
        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();

        // set the properties
        factoryBean.setDataSource(dataSource());
        factoryBean.setPackagesToScan(environment.getProperty("hibernate.packagesToScan"));
        factoryBean.setHibernateProperties(getHibernateProperties());
        return factoryBean;
    }

    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory factory) {

        // setup transaction manager based on session factory
        HibernateTransactionManager manager = new HibernateTransactionManager();
        manager.setSessionFactory(factory);
        return manager;
    }
}
