package com.kais.practiceTest.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Range;


@Entity
public class Product implements Serializable {

	@Override
	public int hashCode() {
		return this.name.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (!(obj instanceof Product)) return false; 
		Product p = (Product)obj;
		return p.getName().equals(this.getName()) && p.getPrice().equals(this.getPrice()) && p.getCategorie().equals(this.getCategorie());
	}

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	
	@NotBlank
	private String name;
    
	@Range(min = 1, max = 10000)
	private Double price;
	
	@NotBlank
	private String categorie;

	public Product() {
	}

	public Product(String name, Double price, String categorie) {
		super();
		this.name = name;
		this.price = price;
		this.categorie = categorie;
	}
	
	public Product(Integer id, @NotBlank String name, @Range(min = 1, max = 200) Double price,
			@NotBlank String categorie) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.categorie = categorie;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getCategorie() {
		return categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}






}
