package com.walmart.ticketbooking.database.repository;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.walmart.ticketbooking.database.model"})
@EnableJpaRepositories(basePackages = {"com.walmart.ticketbooking.database.repository"} )
@PropertySource("classpath:application.properties")
public class RepositoryTestConfig {

}
