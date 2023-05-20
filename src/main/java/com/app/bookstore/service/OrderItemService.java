package com.app.bookstore.service;

import java.util.List;

import com.app.bookstore.entity.OrderItem;

public interface OrderItemService {
    OrderItem saveOrderItem(OrderItem orderItem);

    List<OrderItem> getOrderItems();

    OrderItem getOrderItemById(Long id);

    
}
