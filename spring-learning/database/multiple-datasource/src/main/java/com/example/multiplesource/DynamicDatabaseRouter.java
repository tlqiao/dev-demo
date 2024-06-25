package com.example.multiplesource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
//@Configuration这个注解可以加在类上，让这个类的功能等同于一个bean xml配置文件
@EnableJpaRepositories(basePackages = "com.example.multiplesource",entityManagerFactoryRef = "entityManager")
public class DynamicDatabaseRouter {
    public static final String PROPERTY_PREFIX = "spring.datasource.";
    @Autowired
    private Environment environment;
    @Bean
    //@Bean注解用在方法上，表示通过方法来定义一个bean，默认将方法名称作为bean名称，将方法返回值作为bean对象，注册到spring容器中。
    @Primary
    @Scope("prototype")
    //定义返回默认的Datasource对象
    public AbstractRoutingDataSourceImpl dataSource() {
        Map<Object, Object> targetDataSources = getTargetDataSources();
        return new AbstractRoutingDataSourceImpl((DataSource)targetDataSources.get("default"), targetDataSources);
    }
   //定义返回EntityManager对象
    @Bean(name = "entityManager")
    @Scope("prototype")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(EntityManagerFactoryBuilder builder) {
        return builder.dataSource(dataSource()).packages("com.example.multiplesource").build();
    }
    private Map<Object,Object> getTargetDataSources() {
        //loading the database names to a list from application.properties file
        //从配置文件中读取所有数据库配置信息，并存targetDatSourceMap中
        List<String> databaseNames = environment.getProperty("spring.database-names.list",List.class);
        Map<Object,Object> targetDataSourceMap = new HashMap<>();
        for (String dbName : databaseNames) {
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName(environment.getProperty(PROPERTY_PREFIX + dbName + ".driver"));
            dataSource.setUrl(environment.getProperty(PROPERTY_PREFIX + dbName + ".url"));
            dataSource.setUsername(environment.getProperty(PROPERTY_PREFIX + dbName + ".username"));
            dataSource.setPassword(environment.getProperty(PROPERTY_PREFIX + dbName + ".password"));
            targetDataSourceMap.put(dbName,dataSource);
        }
        //默认将第一个数据库作为默认数据库
        targetDataSourceMap.put("default",targetDataSourceMap.get(databaseNames.get(0)));
        return targetDataSourceMap;
    }
}
//@Bean的不同作用域
//        singleton：该作用域下的Bean在IoC容器中只存在一个实例，spring默认选择的作用域
//        prototype：每次对该作用域下的 Bean 的请求都会创建新的实例
//        request：每次 Http 请求会创建新的Bean实例，类似于 prototype
//        session:在一个 Http Session中，定义一个Bean实例
//        application:在一个Http Servlet Context中，定义一个Bean实例
