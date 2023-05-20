package com.app.bookstore.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.app.bookstore.entity.Book;
import com.app.bookstore.entity.BookCategory;
import com.app.bookstore.exception.BookCategoryNotfoundException;
import com.app.bookstore.exception.BookNotfoundException;
import com.app.bookstore.service.BookCategoryService;

@Component
public class DefaultBookDao implements BookDao {

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private BookCategoryService bookCategoryservice;
    
	@Override
	public Book createBook(Book book) {
		String sql = "INSERT INTO book (name, description, price, bookcategory_id) "
				+ "VALUES (:name, :description, :price, :bookcategory_id)";
		Map<String, Object> params = new HashMap<>();
		params.put("name", book.getName());
		params.put("description", book.getDescription());
		params.put("price", book.getPrice());
		params.put("bookcategory_id", book.getBookcategory().getId());

		KeyHolder keyHolder = new GeneratedKeyHolder(); // Create a KeyHolder to store the generated ID

		jdbcTemplate.update(sql, new MapSqlParameterSource(params), keyHolder, new String[] { "id" }); // Pass the
																										// KeyHolder and
																										// specify the
																										// "id" column

		book.setId(keyHolder.getKey().longValue()); // Retrieve the generated ID and set it in the bookCategory object

		return book;

	}

	@Override
	public Book getBookById(Long bookId) throws BookNotfoundException {
		// TODO Auto-generated method stub
		String sql = "SELECT b.id, b.name, b.description, b.price, c.id AS bookcategory_id, c.name AS bookcategory_name "
				+ "FROM book b " + "JOIN book_category c ON b.bookcategory_id = c.id " + "WHERE b.id = :bookId";

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("bookId", bookId);

		List<Book> books = jdbcTemplate.query(sql, parameters, (rs, rowNum) -> {
			Long id = rs.getLong("id");
			String name = rs.getString("name");
			String description = rs.getString("description");
			BigDecimal price = rs.getBigDecimal("price");
			Long categoryId = rs.getLong("bookcategory_id");
			String categoryName = rs.getString("bookcategory_name");

			BookCategory bookCategory = new BookCategory(categoryId, categoryName);
			Book book = new Book(id, name, description, price, bookCategory);
			bookCategory.addBook(book);

			return book;
		});

		if (books.isEmpty()) {
			throw new BookNotfoundException();
		}

		return books.get(0);

	}

	@Override
	public List<Book> getAllBooks() {
		// TODO Auto-generated method stub
		String sql = "SELECT b.id, b.name, b.description, b.price, c.id AS bookcategory_id, c.name AS bookcategory_name "
				+ "FROM book b " + "JOIN book_category c ON b.bookcategory_id = c.id";

		return jdbcTemplate.query(sql, (rs, rowNum) -> {
			Long id = rs.getLong("id");
			String name = rs.getString("name");
			String description = rs.getString("description");
			BigDecimal price = rs.getBigDecimal("price");
			Long categoryId = rs.getLong("bookcategory_id");
			String categoryName = rs.getString("bookcategory_name");

			BookCategory bookCategory = new BookCategory(categoryId, categoryName);
			Book book = new Book(id, name, description, price, bookCategory);
			bookCategory.addBook(book);

			return book;
		});
	}

	@Override
	public Book updateBook(Book book) throws BookCategoryNotfoundException, BookNotfoundException {
		// TODO Auto-generated method stub
		String sql = "UPDATE book SET name = :name, description = :description, price = :price, bookcategory_id = :bookcategory_id "
				+ "WHERE id = :id";

		Optional<Book> existingBookOptional = Optional.ofNullable(getBookById(book.getId()));
		if (existingBookOptional.isEmpty()) {
			throw new BookNotfoundException();
		}

		Book existingBook = existingBookOptional.get();

		Optional<BookCategory> bookcategory = Optional
				.ofNullable(bookCategoryservice.getBookCategoryById(book.getBookcategory().getId()));
		if (bookcategory.isEmpty()) {
			throw new BookCategoryNotfoundException();
		}

		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("id", book.getId());
		paramMap.put("name", book.getName());
		paramMap.put("description", book.getDescription());
		paramMap.put("price", book.getPrice());
		paramMap.put("bookcategory_id", book.getBookcategory().getId());

		jdbcTemplate.update(sql, paramMap);

		existingBook.setName(book.getName());
		existingBook.setDescription(book.getDescription());
		existingBook.setPrice(book.getPrice());
		existingBook.setBookcategory(bookcategory.get());

		return existingBook;

	}

	@Override
	public void deleteBook(Long bookId) throws BookNotfoundException {
    	try {
			Optional<Book> existingBookOptional = Optional.ofNullable(getBookById(bookId));
	    	String sql = "DELETE FROM book WHERE id = :id";
	        MapSqlParameterSource paramSource = new MapSqlParameterSource("id", bookId);
	        jdbcTemplate.update(sql, paramSource);

		} catch (BookNotfoundException e) {
			// TODO Auto-generated catch block
			throw new BookNotfoundException();
		}


	}

}
