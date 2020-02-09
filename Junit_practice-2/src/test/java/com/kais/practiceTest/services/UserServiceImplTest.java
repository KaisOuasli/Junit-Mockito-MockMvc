package com.kais.practiceTest.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.kais.practiceTest.dao.ProductRepository;
import com.kais.practiceTest.entities.Product;

@DisplayName("UserServiceImpl Test")
class UserServiceImplTest {

	@Mock
	private ProductRepository productRepository;

	@InjectMocks
	private UserServiceImpl userServiceImpl;

	public UserServiceImplTest() {
		MockitoAnnotations.initMocks(this);
	}

	@Nested
	@DisplayName("saveProduct method")
	class TestSaveProduct {

		@Test
		@DisplayName("when saving non null product")
		void testSaveNonNullProduct() {
			Product product = new Product("test", 12.0, "test");
			userServiceImpl.saveProduct(product);
			verify(productRepository, times(1)).save(product);
		}

		@Test
		@DisplayName("when saving null product ")
		void testSaveNullProduct() {
			when(productRepository.save(null)).thenThrow(IllegalArgumentException.class);
			assertThrows(IllegalArgumentException.class, ()->userServiceImpl.saveProduct(null), ()->"should throw IllegalArgumentException");
		}	
	}

	@Nested
	@DisplayName("getProductById method")
	class getProductByIdTest {

		@Test
		@DisplayName("when getting product by an existing id")
		void when_getting_product_by_an_existing_id() {
			Product product = new Product(1, "test", 100.0, "test");
			when(productRepository.findProductById(1)).thenReturn(Optional.of(product));
			assertEquals(Optional.of(product), userServiceImpl.getPoductById(1), ()->"should return the right product");
			verify(productRepository, times(1)).findProductById(1);
		}

		@Test
		@DisplayName("when getting product by a non existing id")
		void when_getting_product_by_a_non_exisiting_id() {
			when(productRepository.findProductById(1)).thenReturn(Optional.empty());
			assertEquals(Optional.empty(), userServiceImpl.getPoductById(1), ()->"should return empty");
			verify(productRepository, times(1)).findProductById(1);
		}

		@Test
		@DisplayName("when getting product by a null id")
		void when_getting_product_by_a_null_id() {
			when(productRepository.findProductById(null)).thenThrow(IllegalArgumentException.class);
			assertThrows(IllegalArgumentException.class, ()->userServiceImpl.getPoductById(null));
			verify(productRepository, times(1)).findProductById(null);
		}
	}

	@Nested
	@DisplayName("deleteProductById test")
	class deleteProductByIdTest{

		@Test
		@DisplayName("when deleting product by a valid id")
		void when_deleting_product_by_a_valid_id() {
			userServiceImpl.deleteProductById(1);
			verify(productRepository, times(1)).deleteById(1);		
		}
	}

}
