package com.app.bookstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.bookstore.dao.CustomerDao;
import com.app.bookstore.entity.Customer;
import com.app.bookstore.entity.Orders;
import com.app.bookstore.exception.CustomerNotfoundException;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerDao customerDao;

    @Override
    public Customer createCustomer(Customer customer) {
    	return customerDao.createCustomer(customer);
    }

    @Override
    public List<Customer> getAllCustomers() {
    	return customerDao.getAllCustomers();
    }

    @Override
    public Customer updateCustomer(Customer customer) throws CustomerNotfoundException {
    	return customerDao.updateCustomer(customer);
       
    }

    @Override
    public Customer getCustomerById(Long id) throws CustomerNotfoundException {
    	return customerDao.getCustomerById(id);
    }

    @Override
    public List<Orders> getOrdersByCustomerId(Long customerId) throws CustomerNotfoundException {
    	return customerDao.getOrdersByCustomerId(customerId);
    }

    @Override
    public void deleteCustomer(Long customerId) throws CustomerNotfoundException {
    	customerDao.deleteCustomer(customerId);    
    }
}
