package com.app.bookstore.service;

import java.util.List;

import com.app.bookstore.entity.BookCategory;
import com.app.bookstore.exception.BookCategoryNotfoundException;

public interface BookCategoryService {
	
	BookCategory createBookCategory(BookCategory bookCategory);
	
	List<BookCategory> getAllBookCategories();
	
	BookCategory getBookCategoryById(Long id) throws BookCategoryNotfoundException;
	
    BookCategory updateBookCategory(BookCategory bookCategory) throws BookCategoryNotfoundException;

    void deleteBookCategory(Long bookcategoryId) throws BookCategoryNotfoundException;
}
