package com.app.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.bookstore.entity.Book;
import com.app.bookstore.exception.BookCategoryNotfoundException;
import com.app.bookstore.exception.BookNotfoundException;
import com.app.bookstore.service.BookService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;


@RestController
@Tag(name = "Books", description = "The Books API")
@Slf4j
public class BookController implements BookControllerInterface {

    @Autowired
    private BookService bookService;

    @Override
    public ResponseEntity<Book> createBook(@Valid @RequestBody Book book){
    	log.debug("book={}", book);

        Book savedBook = bookService.createBook(book);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    @Override
    @GetMapping("{id}")
    public ResponseEntity<Object> getBookById(@PathVariable("id") Long bookId){
    	log.debug("bookId={}", bookId);
        Book book;
        try {
            book = bookService.getBookById(bookId);
            return new ResponseEntity<>(book, HttpStatus.OK);
        } catch (BookNotfoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Book ID not found", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks(){
        List<Book> books = bookService.getAllBooks();
    
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @Override
    @PutMapping("{id}")
    public ResponseEntity<Object> updateBook(@PathVariable("id") Long bookId,
                                             @Valid @RequestBody Book book){
    	log.debug("bookId={} , book={}", bookId, book);
        book.setId(bookId);
        System.out.println("book id in update is "+ book.getId());
        Book updatedBook;
		try {
			updatedBook = bookService.updateBook(book);
			  return new ResponseEntity<>(updatedBook, HttpStatus.OK);
		} catch (BookCategoryNotfoundException e) {
		
		
			 return new ResponseEntity<>("Book Category ID not found", HttpStatus.NOT_FOUND);
		} catch (BookNotfoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			 return new ResponseEntity<>("Book ID not found", HttpStatus.NOT_FOUND);
		}
      
    }

	@Override
	public ResponseEntity<String> deleteBook(Long bookId) {
		// TODO Auto-generated method stub
    	try {
    		Book book = bookService.getBookById(bookId);
			bookService.deleteBook(bookId);
	        return new ResponseEntity<>("Book successfully deleted!", HttpStatus.OK);
    	} catch (BookNotfoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			 return new ResponseEntity<>("Book ID not found", HttpStatus.NOT_FOUND);

		}

	}

}
	