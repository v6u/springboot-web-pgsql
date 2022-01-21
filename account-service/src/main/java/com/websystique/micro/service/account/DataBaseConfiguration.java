//package com.websystique.micro.service.account;
//
//import com.zaxxer.hikari.HikariDataSource;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.core.env.Environment;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import javax.sql.DataSource;
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
////@EnableTransactionManagement
////@EnableJpaRepositories(basePackages = "com.websystique.micro.service.account.repo")
//public class DataBaseConfiguration {
//
//    private final Environment environment;
//
//    public DataBaseConfiguration(Environment environment) {
//        this.environment = environment;
//    }
//
//    @Bean
//    @ConfigurationProperties("master.datasource")
//    @Primary
//    public DataSourceProperties masterDataSourceProperties() {
//        return new DataSourceProperties();
//    }
//
//    @Bean
//    @ConfigurationProperties("master.datasource.configuration")
//    public HikariDataSource masterDataSource(DataSourceProperties masterDataSourceProperties) {
//        return (HikariDataSource) masterDataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
//    }
//
//    @Bean
//    @ConfigurationProperties("slave.datasource")
//    public DataSourceProperties slaveDataSourceProperties() {
//        return new DataSourceProperties();
//    }
//
//    @Bean
//    @ConfigurationProperties("slave.datasource.configuration")
//    public HikariDataSource slaveDataSource(DataSourceProperties slaveDataSourceProperties) {
//        return (HikariDataSource) slaveDataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
//    }
//
//    @Bean
//    @Primary
//    public TransactionRoutingDataSource routingDataSource(@Qualifier("masterDataSource") DataSource masterDataSource, @Qualifier("slaveDataSource") DataSource slaveDataSource) {
//        TransactionRoutingDataSource routingDataSource = new TransactionRoutingDataSource();
//
//        Map<Object, Object> dataSourceMap = new HashMap<>();
//        dataSourceMap.put(DataSourceType.READ_WRITE, masterDataSource);
//        dataSourceMap.put(DataSourceType.READ_ONLY, slaveDataSource);
//
//        routingDataSource.setTargetDataSources(dataSourceMap);
//        routingDataSource.setDefaultTargetDataSource(masterDataSource);
//
//        return routingDataSource;
//    }
//
////    @Bean
////    public BeanPostProcessor dialectProcessor() {
////
////        return new BeanPostProcessor() {
////            @Override
////            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
////                if (bean instanceof HibernateJpaVendorAdapter) {
////                    ((HibernateJpaVendorAdapter) bean).getJpaDialect().setPrepareConnection(false);
////                }
////                return bean;
////            }
////
////            @Override
////            public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
////                return null;
////            }
////        };
////    }
//
//}


package com.websystique.micro.service.account;

import org.hibernate.jpa.HibernatePersistenceProvider;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.websystique.micro.service.account.repo")
public class DataBaseConfiguration {

    @Resource
    private Environment env;

    @Bean
    public DataSource getDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
//        dataSourceBuilder.driverClassName(
//                env.getProperty("spring.datasource.driverClassName")
//        );
        dataSourceBuilder.url(
                env.getProperty("master.datasource.url")
        );
        dataSourceBuilder.username(
                env.getProperty("master.datasource.username")
        );
        dataSourceBuilder.password(
                env.getProperty("master.datasource.password")
        );
        return dataSourceBuilder.build();
    }
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(getDataSource());
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
//        entityManagerFactoryBean.setJpaProperties();
        entityManagerFactoryBean.setPackagesToScan("com.websystique.micro.service.account.domain");
        return entityManagerFactoryBean;
    }
}