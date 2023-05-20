package com.app.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.bookstore.entity.Customer;
import com.app.bookstore.entity.Orders;
import com.app.bookstore.exception.BookNotfoundException;
import com.app.bookstore.exception.CustomerNotfoundException;
import com.app.bookstore.service.CustomerService;
import com.app.bookstore.service.OrderService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/customerorders")
@Slf4j
public class CustomerOrderController implements CustomerOrderControllerInterface{

	@Autowired
    private CustomerService customerService;

    @Autowired
    private OrderService orderService;

	@GetMapping("{id}")
	public  ResponseEntity<Object> getOrderHistory(@PathVariable("id") Long customerId){
		log.debug("CustomerId={}", customerId);
		try {
			List<Orders> orderResult =  customerService.getOrdersByCustomerId(customerId);
			return new ResponseEntity<>(orderResult, HttpStatus.OK);
		} catch (CustomerNotfoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<>("Customer ID not found", HttpStatus.NOT_FOUND);
		}
	}
	
	// Place Order REST API
	@PostMapping("{id}")
    // http://localhost:8080/api/books/1
    public ResponseEntity<String> placeOrder(@PathVariable("id") Long customerId, @Valid @RequestBody Orders order){
		log.debug("CustomerId={}", customerId);
		Customer customer;
		try {
			customer = customerService.getCustomerById(customerId);
			order =  orderService.saveOrder(customer, order);
			return new ResponseEntity<>("Book order successfully placed!", HttpStatus.OK);
		} catch (CustomerNotfoundException e) {
			e.printStackTrace();
			return new ResponseEntity<>("Customer ID not found", HttpStatus.NOT_FOUND);
		} catch (BookNotfoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<>("Book ID not found", HttpStatus.NOT_FOUND);
		}
		
	}
}
