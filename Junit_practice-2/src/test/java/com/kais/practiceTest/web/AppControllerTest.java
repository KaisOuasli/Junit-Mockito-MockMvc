package com.kais.practiceTest.web;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.kais.practiceTest.entities.Product;
import com.kais.practiceTest.services.IUserService;

class AppControllerTest {

	@Mock
	private IUserService userSrvice;

	@InjectMocks
	private AppController appController;

	private MockMvc mockMvc;

	public AppControllerTest() {
		MockitoAnnotations.initMocks(this);
	}

	@BeforeEach
	public void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(appController)
				.build();
	}

	@Nested
	@DisplayName("getProductById test")
	class GetProductByIdTest{

		@Test
		@DisplayName("when getting product by a valid id")
		void get_by_a_valid_id() throws Exception {
			Product product = new Product(1, "test", 100.0, "test");
			when(userSrvice.getPoductById(Mockito.anyInt())).thenReturn(Optional.of(product));
			mockMvc.perform(MockMvcRequestBuilders.get("/1"))
			.andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.content().string("{\"id\":1,\"name\":\"test\",\"price\":100.0,\"categorie\":\"test\"}"));
			verify(userSrvice, times(1)).getPoductById(Mockito.anyInt());
		}

		@Test
		@DisplayName("when getting product by an invalid id")
		void get_by_invalid_id() throws Exception {
			when(userSrvice.getPoductById(Mockito.anyInt())).thenReturn(Optional.empty());
			mockMvc.perform(MockMvcRequestBuilders.get("/1"))
			.andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()));
			verify(userSrvice, times(1)).getPoductById(Mockito.anyInt());
		}

	}

	@Nested
	@DisplayName("postProdut test")
	class postProductTest{
		
		@Test
		@DisplayName("when posting by a valid content")
		void post_by_a_valid_content() throws Exception {
			mockMvc.perform(MockMvcRequestBuilders.post("/addProduct")
					.content("{\"name\":\"test\",\"price\":100.0,\"categorie\":\"test\"}")
					.header("Content-Type","application/json"))
			.andExpect(MockMvcResultMatchers.status().is(HttpStatus.CREATED.value()));
			verify(userSrvice, times(1)).saveProduct(Mockito.any(Product.class));
			
		}
		
		@Test
		@DisplayName("when posting by an invalid content")
		void post_by_an_invalid_content() throws Exception {
			mockMvc.perform(MockMvcRequestBuilders.post("/addProduct")
					.content("{\"xxxx\":\"test\",\"xxxx\":100.0,\"xxxx\":\"test\"}")
					.header("Content-Type","application/json"))
			.andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()));
		}
	}
	
	
	@Nested
	@DisplayName("putProduct test")
	class PutProductTest{

		@Test
		@DisplayName("when putting product by a valid id")
		 void put_by_a_valid_id() throws Exception {
			Product productToModify = new Product(1, "toModify", 10.0, "toModify");
			Product productModified = new Product(1, "test", 100.0, "test");
			when(userSrvice.getPoductById(1)).thenReturn(Optional.of(productToModify));
			when(userSrvice.saveProduct(Mockito.any(Product.class))).thenReturn(productModified);
			mockMvc.perform(MockMvcRequestBuilders.put("/1")
					.content("{\"name\":\"test\",\"price\":100.0,\"categorie\":\"test\"}")
					.header("Content-Type","application/json"))
			.andExpect(MockMvcResultMatchers.status().is(HttpStatus.CREATED.value()))
			.andExpect(MockMvcResultMatchers.content().string("{\"id\":1,\"name\":\"test\",\"price\":100.0,\"categorie\":\"test\"}"));
			verify(userSrvice, times(1)).getPoductById(1);
			verify(userSrvice, times(1)).saveProduct(productModified);
		}
 
		@Test
		@DisplayName("when putting product by an invalid id")
		void put_by_an_invalid_id() throws Exception {
			when(userSrvice.getPoductById(3)).thenReturn(Optional.empty());
			mockMvc.perform(MockMvcRequestBuilders.put("/3")
					.content("{\"name\":\"test\",\"price\":100.0,\"categorie\":\"test\"}")
					.header("Content-Type","application/json"))
			.andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()));
			verify(userSrvice, times(1)).getPoductById(3);
		}
		 
		@Test
		@DisplayName("when putting product by an invalid content")
		void put_by_an_invalid_content() throws Exception {
			
			mockMvc.perform(MockMvcRequestBuilders.put("/1")
					.content("{\"xxxx\":\"test\",\"xxxx\":100.0,\"xxxx\":\"test\"}")
					.header("Content-Type","application/json"))
			.andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()));
		}
	}
	
	@Test
	@DisplayName("deleteProductById test")
	void when_deleting_product_by_id() throws Exception {
		when(userSrvice.getPoductById(3)).thenReturn(Optional.empty());
		mockMvc.perform(MockMvcRequestBuilders.delete("/3")
		.header("Content-Type","application/json"))
		.andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()));
		verify(userSrvice, times(1)).deleteProductById(3);
		
	}




}
