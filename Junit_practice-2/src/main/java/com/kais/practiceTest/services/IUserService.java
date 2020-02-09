package com.kais.practiceTest.services;

import java.util.Optional;

import com.kais.practiceTest.entities.Product;

public interface IUserService {

	public Optional<Product> getPoductById(Integer id);

	public Product saveProduct(Product product);
	
	public void deleteProductById(Integer id);

}
