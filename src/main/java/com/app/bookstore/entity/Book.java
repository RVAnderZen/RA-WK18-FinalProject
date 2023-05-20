package com.app.bookstore.entity;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Book{

    private Long id;
    
    private String name;
    
    private String description;
    
    private BigDecimal price;
    
    @JsonIgnoreProperties({"books","hibernateLazyInitializer", "handler"}) 
    private BookCategory bookcategory;
    
	public Book(Long id, String name, String description, BigDecimal price, BookCategory bookcategory) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.bookcategory = bookcategory;
	}


}
