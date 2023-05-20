package com.app.bookstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.bookstore.dao.BookCategoryDao;
import com.app.bookstore.entity.BookCategory;
import com.app.bookstore.exception.BookCategoryNotfoundException;

@Service
public class BookCategoryServiceImpl implements BookCategoryService {

	@Autowired
	BookCategoryDao bookCategoryDao;

    @Override
    public BookCategory createBookCategory(BookCategory bookCategory) {
    	return bookCategoryDao.createBookCategory(bookCategory);
    }

    @Override
    public List<BookCategory> getAllBookCategories() {
    	return bookCategoryDao.getAllBookCategories();
    }

    @Override
    public BookCategory getBookCategoryById(Long id) throws BookCategoryNotfoundException {
    	return bookCategoryDao.getBookCategoryById(id);
    }

    @Override
    public BookCategory updateBookCategory(BookCategory bookCategory) throws BookCategoryNotfoundException {
    	return bookCategoryDao.updateBookCategory(bookCategory);
    }

    @Override
    public void deleteBookCategory(Long bookCategoryId) throws BookCategoryNotfoundException {
    	bookCategoryDao.deleteBookCategory(bookCategoryId);
    }
}
