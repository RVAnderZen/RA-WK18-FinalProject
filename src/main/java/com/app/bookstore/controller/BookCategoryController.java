package com.app.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.bookstore.entity.BookCategory;
import com.app.bookstore.exception.BookCategoryNotfoundException;
import com.app.bookstore.service.BookCategoryService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class BookCategoryController implements BookCategoryControllerInterface{

	@Autowired
    private BookCategoryService bookCategoryService;

    // build create Book Category REST API
    public ResponseEntity<BookCategory> createBookCategory(@Valid @RequestBody BookCategory bookCategory){
    	log.debug("bookCategory={}", bookCategory);
        BookCategory savedBook = bookCategoryService.createBookCategory(bookCategory);
        return new ResponseEntity<BookCategory>(savedBook, HttpStatus.CREATED);
    }

    // build get book category by id REST API
    // http://localhost:8080/api/bookcategories/1
    public ResponseEntity<Object> getBookCategoryById(@PathVariable("id") Long bookCategoryId){
    	log.debug("bookCategoryId={}", bookCategoryId);
    	BookCategory bookCategory;
		try {
			bookCategory = bookCategoryService.getBookCategoryById(bookCategoryId);
	        return new ResponseEntity<>(bookCategory, HttpStatus.OK);

		} catch (BookCategoryNotfoundException e) {
			// TODO Auto-generated catch block
			 return new ResponseEntity<>("Book Category ID not found", HttpStatus.NOT_FOUND);
			
		}
    }

    // Build Get All Books Categories REST API
    // http://localhost:8080/api/bookcategories
    public ResponseEntity<List<BookCategory>> getAllBookCategories(){
        List<BookCategory> bookCategories = bookCategoryService.getAllBookCategories();
        return new ResponseEntity<>(bookCategories, HttpStatus.OK);
    }

    // Build Update Book Category REST API
    // http://localhost:8080/api/bookcategories/1
    public ResponseEntity<Object> updateBookCategory(@PathVariable("id") Long bookId,
    		@Valid @RequestBody BookCategory bookCategory){
    	log.debug("bookId={} , bookCategory", bookId , bookCategory);
    	BookCategory updatedBookCategory;
		try {
			updatedBookCategory = bookCategoryService.updateBookCategory(bookCategory);
			  return new ResponseEntity<>(updatedBookCategory, HttpStatus.OK);
		} catch (BookCategoryNotfoundException e) {
		
		
			 return new ResponseEntity<>("Book Category ID not found", HttpStatus.NOT_FOUND);
		} 
      
    }
    
    // Build Delete Book REST API
    public ResponseEntity<String> deleteBook(@PathVariable("id") Long bookId){
    	log.debug("bookId={}", bookId);
    	try {
			bookCategoryService.deleteBookCategory(bookId);
		} catch (BookCategoryNotfoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return new ResponseEntity<>("Book successfully deleted!", HttpStatus.OK);
    }
}
