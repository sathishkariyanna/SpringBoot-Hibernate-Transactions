package com.sathish.transactions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

/*
By default Spring Boot will automatically configures JPA, and creates Spring BEANs related to the JPA. These automatic configurations of the  Spring Boot include:
DataSourceAutoConfiguration
DataSourceTransactionManagerAutoConfiguration
HibernateJpaAutoConfiguration
The purpose of this application is to use Hibernate, therefore, we need to disable the above automatic configurations of the  Spring Boot.*/
/**
 * 
 * @author Sathish
 *
 */
@SpringBootApplication
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
public class SpringBootTransactionsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootTransactionsApplication.class, args);

	}
}