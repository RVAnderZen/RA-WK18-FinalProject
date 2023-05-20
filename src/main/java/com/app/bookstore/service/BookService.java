package com.app.bookstore.service;


import java.util.List;

import com.app.bookstore.entity.Book;
import com.app.bookstore.exception.BookCategoryNotfoundException;
import com.app.bookstore.exception.BookNotfoundException;

public interface BookService {
    Book createBook(Book book);

    Book getBookById(Long bookId) throws BookNotfoundException;

    List<Book> getAllBooks();

    Book updateBook(Book book) throws BookCategoryNotfoundException, BookNotfoundException;

    void deleteBook(Long bookId) throws BookNotfoundException;
}
