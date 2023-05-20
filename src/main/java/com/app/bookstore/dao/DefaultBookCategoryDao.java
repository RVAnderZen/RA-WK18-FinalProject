package com.app.bookstore.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.app.bookstore.entity.BookCategory;
import com.app.bookstore.exception.BookCategoryNotfoundException;
import com.app.bookstore.service.BookCategoryRowMapper;

@Component
public class DefaultBookCategoryDao implements BookCategoryDao{

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    
	@Override
	public BookCategory createBookCategory(BookCategory bookCategory) {
		// TODO Auto-generated method stub
        final String sql = "INSERT INTO book_category (name) VALUES (:name)";
        Map<String, Object> params = new HashMap<>();
        params.put("name", bookCategory.getName());
        KeyHolder keyHolder = new GeneratedKeyHolder(); // Create a KeyHolder to store the generated ID

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(params), keyHolder, new String[]{"id"}); // Pass the KeyHolder and specify the "id" column

        bookCategory.setId(keyHolder.getKey().longValue()); // Retrieve the generated ID and set it in the bookCategory object

        return bookCategory;

	}

	@Override
	public List<BookCategory> getAllBookCategories() {
		// TODO Auto-generated method stub
        final String sql = "SELECT * FROM book_category";
        List<BookCategory> bookCategories = namedParameterJdbcTemplate.query(sql, new BookCategoryRowMapper());
        return bookCategories;
	}

	@Override
	public BookCategory getBookCategoryById(Long id) throws BookCategoryNotfoundException {
		// TODO Auto-generated method stub
        final String sql = "SELECT * FROM book_category WHERE id=:id";
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);
        List<BookCategory> bookCategories = namedParameterJdbcTemplate.query(sql, params, new BookCategoryRowMapper());
        if (bookCategories.isEmpty()) {
            throw new BookCategoryNotfoundException();
        }
        return bookCategories.get(0);
	}

	@Override
	public BookCategory updateBookCategory(BookCategory bookCategory) throws BookCategoryNotfoundException {
		// TODO Auto-generated method stub
        final String sql = "UPDATE book_category SET name=:name WHERE id=:id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", bookCategory.getId());
        params.put("name", bookCategory.getName());
        int count = namedParameterJdbcTemplate.update(sql, params);
        if (count == 0) {
            throw new BookCategoryNotfoundException();
        }
        return bookCategory;
	}

	@Override
	public void deleteBookCategory(Long bookCategoryId) throws BookCategoryNotfoundException {
        final String sql = "DELETE FROM book_category WHERE id=:id";
        MapSqlParameterSource params = new MapSqlParameterSource("id", bookCategoryId);
        int count = namedParameterJdbcTemplate.update(sql, params);
        if (count == 0) {
            throw new BookCategoryNotfoundException();
        }
		
	}

}
