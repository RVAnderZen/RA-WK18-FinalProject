package com.app.bookstore.dao;

import java.util.List;

import com.app.bookstore.entity.Book;
import com.app.bookstore.exception.BookCategoryNotfoundException;
import com.app.bookstore.exception.BookNotfoundException;


public interface BookDao {
	
    Book createBook(Book book);

    Book getBookById(Long bookId) throws BookNotfoundException;

    List<Book> getAllBooks();

    Book updateBook(Book book) throws BookCategoryNotfoundException, BookNotfoundException;

    void deleteBook(Long bookId) throws BookNotfoundException;
}
