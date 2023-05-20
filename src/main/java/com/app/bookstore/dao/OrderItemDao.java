package com.app.bookstore.dao;

import java.util.List;

import com.app.bookstore.entity.OrderItem;

public interface OrderItemDao  {
    OrderItem saveOrderItem(OrderItem orderItem);

    List<OrderItem> getOrderItems();

    OrderItem getOrderItemById(Long id);
}
