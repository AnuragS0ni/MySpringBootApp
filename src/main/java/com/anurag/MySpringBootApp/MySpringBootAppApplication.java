package com.anurag.MySpringBootApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.mongodb.util.MongoCompatibilityAdapter.MongoDatabaseAdapterBuilder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.reactive.TransactionalOperatorExtensionsKt;

import com.anurag.MySpringBootApp.repositries.JournalRepository;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.anurag.MySpringBootApp")
@EnableTransactionManagement
public class MySpringBootAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(MySpringBootAppApplication.class, args);
	}
	@Bean
	public PlatformTransactionManager pftm(MongoDatabaseFactory dbFactory)
	{
		return new MongoTransactionManager(dbFactory);
	}

}
