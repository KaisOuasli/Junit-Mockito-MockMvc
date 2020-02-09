package com.kais.practiceTest.web;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.kais.practiceTest.entities.Product;
import com.kais.practiceTest.services.IUserService;

@RestController
public class AppController {


	private final IUserService userService;


	@Autowired
	public AppController(IUserService userService) {
		this.userService = userService;
	}

	@GetMapping("/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable Integer id){
		Optional<Product> optProduct = userService.getPoductById(id);
		if(optProduct.isPresent())
			return new ResponseEntity<>(optProduct.get(), HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@PostMapping("/addProduct")
	public ResponseEntity<Product> postProduct(@Valid @RequestBody Product product) {
		Product savedProduct = userService.saveProduct(product);
		return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Product> putproduct(@PathVariable Integer id, @Valid @RequestBody Product product){
		Optional<Product> optProduct = userService.getPoductById(id);
		if(optProduct.isPresent()) {
		Product productToSet = optProduct.get();
		productToSet.setCategorie(product.getCategorie());
		productToSet.setName(product.getName());
		productToSet.setPrice(product.getPrice());
		Product putProduct = userService.saveProduct(productToSet);
		return new ResponseEntity<>(putProduct, HttpStatus.CREATED);	
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteProductById(@PathVariable Integer id){
		userService.deleteProductById(id);
		return new ResponseEntity<>("product with id:"+id+" is deleted with success!", HttpStatus.OK);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(
			MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}
}


