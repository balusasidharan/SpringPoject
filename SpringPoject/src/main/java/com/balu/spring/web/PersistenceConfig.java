package com.balu.spring.web;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.balu.spring.model.Person;

@Configuration
@EnableTransactionManagement
public class PersistenceConfig {
	
	@Bean
	public  BasicDataSource datasource(){
		BasicDataSource  ds = new BasicDataSource();
		ds.setDriverClassName("org.postgresql.Driver");
		ds.setUrl("jdbc:postgresql://localhost:5432/springdb");
		ds.setUsername("postgres");
		ds.setPassword("admin");
		ds.setInitialSize(10);
		ds.setMaxActive(5);
		ds.setMaxIdle(5);
		System.out.println("Data Source:" + ds);
		return ds;
		
	}
	
	@Bean
	@Autowired
	public LocalSessionFactoryBean sessionFactory (DataSource dataSource){
		LocalSessionFactoryBean sfb = new LocalSessionFactoryBean();
		sfb.setDataSource(dataSource);
		sfb.setAnnotatedClasses(Person.class);
		Properties props = new Properties();
		props.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		props.setProperty("hibernate.show_sql", "true");
		sfb.setHibernateProperties(props);
		return sfb;
		
		
	}
	
	   @Bean
	   @Autowired
	   public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
	      HibernateTransactionManager txManager = new HibernateTransactionManager();
	      txManager.setSessionFactory(sessionFactory);
	 
	      return txManager;
	   }
	 

}
