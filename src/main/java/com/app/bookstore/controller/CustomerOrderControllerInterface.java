package com.app.bookstore.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.bookstore.entity.Orders;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;
import jakarta.validation.Valid;


@RequestMapping("api/customerorders")
@OpenAPIDefinition(info = @io.swagger.v3.oas.annotations.info.Info(title = "Customer Order Service"), servers = {
		@Server(url = "http://localhost:8080", description = "Local Server.") })
public interface CustomerOrderControllerInterface {

    @Operation(
        summary = "Get Order History",
        description = "Retrieves the order history of a customer",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Order history successfully retrieved",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Orders.class))
            ),
            @ApiResponse(responseCode = "404", description = "Customer not found")
        }
    )
    @GetMapping("{id}")
    ResponseEntity<Object> getOrderHistory(
        @Parameter(description = "ID of the customer") @PathVariable("id") Long customerId
    );

    @Operation(
        summary = "Place Order",
        description = "Places an order for a customer",
        responses = {
            @ApiResponse(responseCode = "200", description = "Order successfully placed"),
            @ApiResponse(responseCode = "404", description = "Customer or book not found")
        }
    )
    @PostMapping("{id}")
    ResponseEntity<String> placeOrder(
        @Parameter(description = "ID of the customer") @PathVariable("id") Long customerId,
        @Parameter(description = "Order details") @Valid @RequestBody Orders order
    );
}
