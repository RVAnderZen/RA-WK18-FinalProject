package com.app.bookstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.bookstore.dao.OrderDao;
import com.app.bookstore.entity.Customer;
import com.app.bookstore.entity.OrderItem;
import com.app.bookstore.entity.Orders;
import com.app.bookstore.exception.BookNotfoundException;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderDao orderDao;

    @Override
    public Orders getOrderById(Long id) {
    	return orderDao.getOrderById(id);
    }

    @Override
    public OrderItem getOrderItemById(Long itemId) {
    	return orderDao.getOrderItemById(itemId);
    }

    @Override
    public Orders saveOrder(Customer customer, Orders order) throws BookNotfoundException {
    	return orderDao.saveOrder(customer, order);
    }

    @Override
    public Orders updateOrder(Orders order) {
    	return orderDao.updateOrder(order);
    }

    @Override
    public void completeOrder(Orders order) {
    	orderDao.completeOrder(order);
    }

    @Override
    public OrderItem saveOrderItem(OrderItem orderItem) {
        // Save order item using the existing implementation or refactor it to use the jdbcTemplate
    	return orderDao.saveOrderItem(orderItem);
    }

    @Override
    public void cancelOrder(Orders order) {
    	orderDao.cancelOrder(order);
    }

    @Override
    public List<Orders> getAll() {
    	return orderDao.getAll();
    }
}
