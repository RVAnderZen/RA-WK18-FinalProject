package com.app.bookstore.service;

import java.util.List;

import com.app.bookstore.entity.Customer;
import com.app.bookstore.entity.OrderItem;
import com.app.bookstore.entity.Orders;
import com.app.bookstore.exception.BookNotfoundException;

public interface OrderService {
    Orders getOrderById(Long id);
    Orders saveOrder(Customer customer, Orders order) throws BookNotfoundException;
    Orders updateOrder(Orders order);
    OrderItem saveOrderItem(OrderItem orderItem);
    void completeOrder(Orders order);
    void cancelOrder(Orders order);
    OrderItem getOrderItemById(Long itemId);
    List<Orders> getAll();
}
