package com.zeeshan.multiple.ds.api.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "bookEntityManagerFactory",
		//配置连接工厂 entityManagerFactory
		transactionManagerRef = "bookTransactionManager",
		//配置事物管理器 transactionManager
		basePackages = {"com.zeeshan.multiple.ds.api.book.repository" })
         //设置持久层所在位置，这样不同的repository会使用不同的数据库配置信息，从而实现多数据库切换
public class BookDBConfig {
	@Bean(name = "bookDataSource")
	@ConfigurationProperties(prefix = "spring.book.datasource")
	//通过前缀区分不同的数据库配置
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}
	@Bean(name = "bookEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean bookEntityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier("bookDataSource") DataSource dataSource) {
		return builder.dataSource(dataSource)
				//设置实体类所在位置.扫描所有带有 @Entity 注解的类
				.packages("com.zeeshan.multiple.ds.api.model.book")
				.persistenceUnit("myBook").build();
		// Spring会将EntityManagerFactory注入到Repository之中.有了 EntityManagerFactory之后,
		// Repository就能用它来创建EntityManager了,然后 EntityManager 就可以针对数据库执行操作
	}
	@Bean(name = "bookTransactionManager")
	public PlatformTransactionManager bookTransactionManager(
			@Qualifier("bookEntityManagerFactory") EntityManagerFactory bookEntityManagerFactory) {
		return new JpaTransactionManager(bookEntityManagerFactory);
	}
}
