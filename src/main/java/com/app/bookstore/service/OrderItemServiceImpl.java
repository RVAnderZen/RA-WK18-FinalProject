package com.app.bookstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.bookstore.dao.OrderItemDao;
import com.app.bookstore.entity.OrderItem;

@Service
public class OrderItemServiceImpl implements OrderItemService {

	@Autowired    
	private OrderItemDao orderItemDao;
	
    @Override
    public OrderItem saveOrderItem(OrderItem orderItem) {
    	return orderItemDao.saveOrderItem(orderItem);
    }

    @Override
    public List<OrderItem> getOrderItems() {
    	return orderItemDao.getOrderItems();
    }

    @Override
    public OrderItem getOrderItemById(Long id) {
    	return orderItemDao.getOrderItemById(id);
    }

}
