package com.app.bookstore.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookCategory {
    private Long id;
    
    private String name;

    @JsonIgnoreProperties({"bookcategory","hibernateLazyInitializer", "handler"}) 
    List<Book> books = new ArrayList<Book>();


	public BookCategory(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public void addBook(Book book) {
        books.add(book);
    }

    public void removeBook(Book book) {
        books.remove(book);
    }

}
