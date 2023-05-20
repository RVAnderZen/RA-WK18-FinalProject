package com.app.bookstore.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.app.bookstore.entity.BookCategory;

public class BookCategoryRowMapper implements RowMapper<BookCategory> {

    @Override
    public BookCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long id = rs.getLong("id");
        String name = rs.getString("name");
        BookCategory bookCategory = new BookCategory(id, name);
        return bookCategory;
    }

}
