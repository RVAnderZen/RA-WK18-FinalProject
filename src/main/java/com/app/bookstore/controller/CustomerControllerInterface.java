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

import com.app.bookstore.entity.Customer;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;
import jakarta.validation.Valid;

@RequestMapping("api/customers")
@OpenAPIDefinition(info = @io.swagger.v3.oas.annotations.info.Info(title = "Customer Service"), servers = {
		@Server(url = "http://localhost:8080", description = "Local Server.") })
public interface CustomerControllerInterface {

    @Operation(
        summary = "Create a Customer",
        description = "Creates a new customer",
        responses = {
            @ApiResponse(
                responseCode = "201",
                description = "Customer successfully created",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Customer.class))
            )
        }
    )
    @PostMapping
    ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer);

    @Operation(
        summary = "Get Customer by ID",
        description = "Retrieves a customer by ID",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Customer successfully retrieved",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Customer.class))
            ),
            @ApiResponse(responseCode = "404", description = "Customer not found")
        }
    )
    @GetMapping("{id}")
    ResponseEntity<Object> getCustomerById(
        @Parameter(description = "ID of the customer to retrieve") @PathVariable("id") Long customerId
    );

    @Operation(
        summary = "Get All Customers",
        description = "Retrieves all customers",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Customers successfully retrieved",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Customer.class))
            )
        }
    )
    @GetMapping
    ResponseEntity<List<Customer>> getAllCustomers();

    @Operation(
        summary = "Update a Customer",
        description = "Updates a customer by ID",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Customer successfully updated",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Customer.class))
            ),
            @ApiResponse(responseCode = "404", description = "Customer not found")
        }
    )
    @PutMapping("{id}")
    ResponseEntity<Object> updateCustomer(
        @Parameter(description = "ID of the customer to update") @PathVariable("id") Long customerId,
        @Parameter(description = "Updated customer details") @Valid @RequestBody Customer customer
    );

    @Operation(
        summary = "Delete a Customer",
        description = "Deletes a customer by ID",
        responses = {
            @ApiResponse(responseCode = "200", description = "Customer successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
        }
    )
    @DeleteMapping("{id}")
    ResponseEntity<String> deleteCustomer(
        @Parameter(description = "ID of the customer to delete") @PathVariable("id") Long customerId
    );
}
