package com.app.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.bookstore.entity.Customer;
import com.app.bookstore.exception.CustomerNotfoundException;
import com.app.bookstore.service.CustomerService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CustomerController implements CustomerControllerInterface {

	
	@Autowired
    private CustomerService customerService;

    // build create Customer REST API
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer){
    	log.debug("Customer={}", customer);
        Customer savedCustomer = customerService.createCustomer(customer);
        return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
    }
    
    // build get Customer by id REST API
    // http://localhost:8080/api/Customers/1
    public ResponseEntity<Object> getCustomerById(@PathVariable("id") Long customerId){
    	log.debug("CustomerId={}", customerId);
    	try{
    		Customer Customer = customerService.getCustomerById(customerId);
    		return new ResponseEntity<>(Customer, HttpStatus.OK);
    	}catch (CustomerNotfoundException e) {
    		e.printStackTrace();
    		return new ResponseEntity<>("Customer ID not found", HttpStatus.NOT_FOUND);
    	}
    }

    // Build Get All Customers REST API
    // http://localhost:8080/api/Customers
    public ResponseEntity<List<Customer>> getAllCustomers(){
        List<Customer> Customers = customerService.getAllCustomers();
        return new ResponseEntity<>(Customers, HttpStatus.OK);
    }

    // Build Update Customer REST API
    // http://localhost:8080/api/Customers/1
    public ResponseEntity<Object> updateCustomer(@PathVariable("id") Long customerId,
    		@Valid @RequestBody Customer customer){
    	log.debug("CustomerId={} , Customer={}", customerId,customer);
    	customer.setId(customerId);
        Customer updatedCustomer;
		try {
			updatedCustomer = customerService.updateCustomer(customer);
			return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
		}catch (CustomerNotfoundException e) {
			 e.printStackTrace();
			 return new ResponseEntity<>("Customer ID not found", HttpStatus.NOT_FOUND);
		}
      
    }

    // Build Delete Customer REST API
    public ResponseEntity<String> deleteCustomer(@PathVariable("id") Long customerId){
    	log.debug("CustomerId={}", customerId);
        try {
			customerService.deleteCustomer(customerId);
	        return new ResponseEntity<>("Customer successfully deleted!", HttpStatus.OK);

		} catch (CustomerNotfoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<>("Customer ID not found", HttpStatus.NOT_FOUND);
		}
    }
}
