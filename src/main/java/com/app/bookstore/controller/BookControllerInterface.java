package com.app.bookstore.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.bookstore.entity.Book;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;
import jakarta.validation.Valid;

@RequestMapping("api/books")
@OpenAPIDefinition(info = @io.swagger.v3.oas.annotations.info.Info(title = "Bookstore Service"), servers = {
		@Server(url = "http://localhost:8080", description = "Local Server.") })

public interface BookControllerInterface {
	// build get book by id REST API
	// http://localhost:8080/api/books/1
	@GetMapping("{id}")
	@Operation(summary = "Get a book by ID", responses = {
			@ApiResponse(responseCode = "200", description = "Found the book", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) }),
			@ApiResponse(responseCode = "404", description = "Book not found", content = @Content(mediaType = "application/json")) })
	ResponseEntity<Object> getBookById(@PathVariable("id") Long bookId);

	// Build Get All Books REST API
	// http://localhost:8080/api/books
	@GetMapping
	@Operation(summary = "Get all books", responses = {
			@ApiResponse(responseCode = "200", description = "Found the books", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) }), })
	ResponseEntity<List<Book>> getAllBooks();

	// Build Update Book REST API
	@PutMapping("{id}")
	@Operation(summary = "Update a book by ID", responses = {
			@ApiResponse(responseCode = "200", description = "Updated the book", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) }),
			@ApiResponse(responseCode = "404", description = "Book not found", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json")) })
	ResponseEntity<Object> updateBook(@PathVariable("id") Long bookId, @Valid @RequestBody Book book);

	@Operation(summary = "Delete a Book", description = "Deletes a book  by ID", responses = {
			@ApiResponse(responseCode = "200", description = "Book successfully deleted"),
			@ApiResponse(responseCode = "404", description = "Book not found") })
	@DeleteMapping("{id}")
	ResponseEntity<String> deleteBook(Long bookId);

	@Operation(summary = "Create a book", description = "Creates a new book in the database.", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The book to be created.", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class))), responses = {
					@ApiResponse(responseCode = "201", description = "Book successfully created.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class))) })
	@PostMapping
	ResponseEntity<Book> createBook(@Valid Book book);
}