package com.kais.practiceTest.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kais.practiceTest.dao.ProductRepository;
import com.kais.practiceTest.entities.Product;


@Service
@Transactional
public class UserServiceImpl implements IUserService{

	private final ProductRepository productRepository;

	@Autowired
	public UserServiceImpl(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Override
	public Optional<Product> getPoductById(Integer id) {
		return productRepository.findProductById(id);
	}

	@Override
	public Product saveProduct(Product product) {
		return productRepository.save(product);
	}

	@Override
	public void deleteProductById(Integer id) {
		productRepository.deleteById(id);
	}

}
