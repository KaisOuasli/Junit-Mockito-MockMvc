package com.kais.practiceTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.kais.practiceTest.dao.ProductRepository;
import com.kais.practiceTest.entities.Product;

@SpringBootApplication
public class JunitPractice2Application implements CommandLineRunner{

	@Autowired
	ProductRepository productRepository;

	public static void main(String[] args) {
		SpringApplication.run(JunitPractice2Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		productRepository.save(new Product("chemise", 12.5, "vetement"));
		productRepository.save(new Product("vase", 30.2, "decoration"));
	}

}
