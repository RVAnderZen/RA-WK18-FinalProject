package com.app.bookstore.dao;

import java.util.List;

import com.app.bookstore.entity.Customer;
import com.app.bookstore.entity.Orders;
import com.app.bookstore.exception.CustomerNotfoundException;

public interface CustomerDao  {
	
	Customer createCustomer(Customer customer);
	Customer updateCustomer(Customer customer) throws CustomerNotfoundException;
    Customer getCustomerById(Long id) throws CustomerNotfoundException;
    List<Orders> getOrdersByCustomerId(Long customerId) throws CustomerNotfoundException;
	List<Customer> getAllCustomers();
	void deleteCustomer(Long customerId) throws CustomerNotfoundException;
}


