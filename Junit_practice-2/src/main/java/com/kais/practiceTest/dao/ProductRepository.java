package com.kais.practiceTest.dao;


import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.kais.practiceTest.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
	
public Optional<Product> findProductById(Integer id);


}
