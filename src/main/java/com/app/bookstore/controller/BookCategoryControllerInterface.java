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

import com.app.bookstore.entity.BookCategory;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;
import jakarta.validation.Valid;

@RequestMapping("api/bookcategories")
@OpenAPIDefinition(info = @io.swagger.v3.oas.annotations.info.Info(title = "Book Category Service"), servers = {
		@Server(url = "http://localhost:8080", description = "Local Server.") })
public interface BookCategoryControllerInterface {
	// build create Book Category REST API
	@PostMapping
	@Operation(summary = "Create a new book category", responses = {
			@ApiResponse(responseCode = "201", description = "Created the book category", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = BookCategory.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json")) })
	ResponseEntity<BookCategory> createBookCategory(@Valid @RequestBody BookCategory bookCategory);

	// build get book category by id REST API
	// http://localhost:8080/api/bookcategories/1
	@GetMapping("{id}")
	@Operation(summary = "Get a book category by ID", responses = {
			@ApiResponse(responseCode = "200", description = "Found the book category", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = BookCategory.class)) }),
			@ApiResponse(responseCode = "404", description = "Book category not found", content = @Content(mediaType = "application/json")) })
	ResponseEntity<Object> getBookCategoryById(@PathVariable("id") Long bookCategoryId);

	// Build Get All Books Categories REST API
	// http://localhost:8080/api/bookcategories
	@GetMapping
	@Operation(summary = "Get all book categories", responses = {
			@ApiResponse(responseCode = "200", description = "Found the book categories", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = BookCategory.class)) }), })
	ResponseEntity<List<BookCategory>> getAllBookCategories();

	@Operation(summary = "Delete a Book Category", description = "Deletes a book category by ID", responses = {
			@ApiResponse(responseCode = "200", description = "Book category successfully deleted"),
			@ApiResponse(responseCode = "404", description = "Book category not found") })
	@DeleteMapping("{id}")
	ResponseEntity<String> deleteBook(
			@Parameter(description = "ID of the book category to delete") @PathVariable("id") Long bookId);

	@Operation(summary = "Update a Book Category", description = "Updates a book category by ID", responses = {
			@ApiResponse(responseCode = "200", description = "Book category successfully updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookCategory.class))),
			@ApiResponse(responseCode = "404", description = "Book category not found") })
	@PutMapping("{id}")
	ResponseEntity<Object> updateBookCategory(
			@Parameter(description = "ID of the book category to update") @PathVariable("id") Long bookId,
			@Parameter(description = "Updated book category details") @Valid @RequestBody BookCategory bookCategory);

}
