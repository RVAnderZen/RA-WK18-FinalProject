package com.app.bookstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.bookstore.dao.BookDao;
import com.app.bookstore.entity.Book;
import com.app.bookstore.exception.BookCategoryNotfoundException;
import com.app.bookstore.exception.BookNotfoundException;

@Service
public class BookServiceImpl implements BookService {



    @Autowired
    private BookDao bookDao;
    
    @Override
    public Book createBook(Book book) {
    	return bookDao.createBook(book);
    }

    @Override
    public Book getBookById(Long bookId) throws BookNotfoundException {
    	return bookDao.getBookById(bookId);
    }

    @Override
    public List<Book> getAllBooks() {
    	return bookDao.getAllBooks();
    }

    @Override
    public Book updateBook(Book book) throws BookCategoryNotfoundException, BookNotfoundException {
    	return bookDao.updateBook(book);
    }
    
    @Override
    public void deleteBook(Long bookId) throws BookNotfoundException {
    	bookDao.deleteBook(bookId);
    }
}

