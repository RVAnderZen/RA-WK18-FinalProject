package com.app.bookstore.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.app.bookstore.entity.Customer;
import com.app.bookstore.entity.Orders;
import com.app.bookstore.exception.CustomerNotfoundException;

@Component
public class DefaultCustomerDao implements CustomerDao{

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Override
	public Customer createCustomer(Customer customer) {
	
        String sql = "INSERT INTO customer (first_name, last_name, email, phone, address, register_date) " +
                "VALUES (:firstName, :lastName, :email, :phone, :address, :registerDate)";
        Map<String, Object> params = new HashMap<>();
        params.put("firstName", customer.getFirstName());
        params.put("lastName", customer.getLastName());
        params.put("email", customer.getEmail());
        params.put("phone", customer.getPhone());
        params.put("address", customer.getAddress());
        params.put("registerDate", customer.getRegisterDate());

        KeyHolder keyHolder = new GeneratedKeyHolder(); // Create a KeyHolder to store the generated ID
        jdbcTemplate.update(sql, new MapSqlParameterSource(params), keyHolder, new String[]{"id"}); // Pass the KeyHolder and specify the "id" column
        customer.setId(keyHolder.getKey().longValue()); // Retrieve the generated ID and set it in the bookCategory object

        return customer;

	}

	@Override
	public Customer updateCustomer(Customer customer) throws CustomerNotfoundException {
	
    	try {
			Customer persistedCustomer = getCustomerById(customer.getId());
	        String sql = "UPDATE customer SET first_name = :firstName, last_name = :lastName, email = :email, " +
	                "phone = :phone, address = :address WHERE id = :id";
	        MapSqlParameterSource params = new MapSqlParameterSource()
	                .addValue("firstName", customer.getFirstName())
	                .addValue("lastName", customer.getLastName())
	                .addValue("email", customer.getEmail())
	                .addValue("phone", customer.getPhone())
	                .addValue("address", customer.getAddress())
	                .addValue("id", customer.getId());

	        jdbcTemplate.update(sql, params);

	        return customer;
		} catch (CustomerNotfoundException e) {
			
			throw new CustomerNotfoundException();
		}

	}

	@Override
	public Customer getCustomerById(Long id) throws CustomerNotfoundException {
        String sql = "SELECT * FROM customer WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("id", id);
        List<Customer> customers = jdbcTemplate.query(sql, params, (rs, rowNum) -> {
            Customer customer = new Customer();
            customer.setId(rs.getLong("id"));
            customer.setFirstName(rs.getString("first_name"));
            customer.setLastName(rs.getString("last_name"));
            customer.setEmail(rs.getString("email"));
            customer.setPhone(rs.getString("phone"));
            customer.setAddress(rs.getString("address"));
            customer.setRegisterDate(rs.getDate("register_date").toLocalDate());
            return customer;
        });

        if (customers.isEmpty()) {
            throw new CustomerNotfoundException();
        }

        return customers.get(0);

	}

	@Override
	public List<Orders> getOrdersByCustomerId(Long customerId) throws CustomerNotfoundException {
		
		  getCustomerById(customerId);

	        String sql = "SELECT * FROM orders WHERE customer_id = :customerId";
	        MapSqlParameterSource params = new MapSqlParameterSource().addValue("customerId", customerId);
	        return jdbcTemplate.query(sql, params, (rs, rowNum) -> {
	            Orders order = new Orders();
	            order.setId(rs.getLong("id"));
	            // Populate other properties of the Order object
	            return order;
	        });
	}

	@Override
	public List<Customer> getAllCustomers() {
        String sql = "SELECT * FROM customer";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Customer customer = new Customer();
            customer.setId(rs.getLong("id"));
            customer.setFirstName(rs.getString("first_name"));
            customer.setLastName(rs.getString("last_name"));
            customer.setEmail(rs.getString("email"));
            customer.setPhone(rs.getString("phone"));
            customer.setAddress(rs.getString("address"));
            customer.setRegisterDate(rs.getDate("register_date").toLocalDate());
            return customer;
        });
	}

	@Override
	public void deleteCustomer(Long customerId) throws CustomerNotfoundException {
	    String sql = "DELETE FROM customer WHERE id = :customerId";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("customerId", customerId);
        int count = jdbcTemplate.update(sql, params);
        if (count == 0) {
            throw new CustomerNotfoundException();
        }
		
	}

}
