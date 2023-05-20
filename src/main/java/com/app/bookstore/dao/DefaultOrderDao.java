package com.app.bookstore.dao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.app.bookstore.entity.Book;
import com.app.bookstore.entity.Customer;
import com.app.bookstore.entity.OrderItem;
import com.app.bookstore.entity.OrderItemStatus;
import com.app.bookstore.entity.OrderStatus;
import com.app.bookstore.entity.Orders;
import com.app.bookstore.exception.BookNotfoundException;
import com.app.bookstore.service.BookService;
import com.app.bookstore.service.OrderItemService;

@Component
public class DefaultOrderDao implements OrderDao{

	
	@Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Autowired
    private OrderItemService orderItemService;
	
	@Autowired
    private BookService bookService;
    
	@Override
	public Orders getOrderById(Long id) {
        String query = "SELECT * FROM orders WHERE id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        return jdbcTemplate.queryForObject(query, params, (resultSet, rowNum) -> {
            Orders order = new Orders();
            order.setId(resultSet.getLong("id"));
            // Set other order properties from the result set
            return order;
        });

	}

	@Override
	public Orders saveOrder(Customer customer, Orders order) throws BookNotfoundException {
        List<OrderItem> savedOrderItemsLst = new ArrayList<>();
        for (OrderItem orderItem : order.getOrderItems()) {
            Book bookFromDb = bookService.getBookById(orderItem.getBook().getId());
            orderItem.setBook(bookFromDb);
            OrderItem savedOrderItem = orderItemService.saveOrderItem(orderItem);
            savedOrderItemsLst.add(savedOrderItem);
        }
        BigDecimal totalAmount = BigDecimal.ZERO;
        order.setOrderItems(savedOrderItemsLst);
        order.setTotalAmount(totalAmount);
        order.setCustomer(customer);
        order.setOrderedDate(LocalDateTime.now());

        String insertOrderQuery = "INSERT INTO orders(customer_id, total_amount, ordered_date, shipping_address, " +
                "billing_address, payment_method, status) " +
                "VALUES (:customerId, :totalAmount, :orderedDate, :shippingAddress, :billingAddress, " +
                ":paymentMethod, :status)";
        MapSqlParameterSource orderParams = new MapSqlParameterSource();
        orderParams.addValue("customerId", order.getCustomer().getId());
        orderParams.addValue("totalAmount", order.getTotalAmount());
        orderParams.addValue("orderedDate", order.getOrderedDate());
        orderParams.addValue("shippingAddress", order.getShippingAddress());
        orderParams.addValue("billingAddress", order.getBillingAddress());
        orderParams.addValue("paymentMethod", order.getPaymentMethod());
        orderParams.addValue("status", order.getStatus().toString());

        jdbcTemplate.update(insertOrderQuery, orderParams);

        String selectLastInsertIdQuery = "SELECT LAST_INSERT_ID()";
        Long orderId = jdbcTemplate.queryForObject(selectLastInsertIdQuery, new HashMap<>(), Long.class);
        order.setId(orderId);

        for (OrderItem orderItem : savedOrderItemsLst) {
            orderItem.setOrder(order);
            orderItemService.saveOrderItem(orderItem);
        }

        customer.addOrder(order);
        return order;

	}

	@Override
	public Orders updateOrder(Orders order) {
        String updateOrderQuery = "UPDATE orders SET total_amount = :totalAmount, ordered_date = :orderedDate, " +
                "shipping_address = :shippingAddress, billing_address = :billingAddress, " +
                "payment_method = :paymentMethod, status = :status WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("totalAmount", order.getTotalAmount());
        params.addValue("orderedDate", order.getOrderedDate());
        params.addValue("shippingAddress", order.getShippingAddress());
        params.addValue("billingAddress", order.getBillingAddress());
        params.addValue("paymentMethod", order.getPaymentMethod());
        params.addValue("status", order.getStatus().toString());
        params.addValue("id", order.getId());

        jdbcTemplate.update(updateOrderQuery, params);
        return order;

	}

	@Override
	public OrderItem saveOrderItem(OrderItem orderItem) {
		   return orderItemService.saveOrderItem(orderItem);
	}

	@Override
	public void completeOrder(Orders order) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cancelOrder(Orders order) {
        order.setStatus(OrderStatus.CANCELED);
        order.setTotalAmount(BigDecimal.ZERO);
        List<OrderItem> orderItems = order.getOrderItems();
        for (OrderItem i : orderItems) {
            i.setOrderStatus(OrderItemStatus.CANCELED);
        }
        if (orderItems != null) {
            orderItems.forEach(orderItemObj -> orderItemObj.setOrderStatus(OrderItemStatus.CANCELED));
        }
        updateOrder(order);

		
	}

	@Override
	public OrderItem getOrderItemById(Long itemId) {
		   String query = "SELECT * FROM order_items WHERE id = :itemId";
	        Map<String, Object> params = new HashMap<>();
	        params.put("itemId", itemId);
	        return jdbcTemplate.queryForObject(query, params, (resultSet, rowNum) -> {
	            OrderItem orderItem = new OrderItem();
	            orderItem.setId(resultSet.getLong("id"));
	            // Set other order item properties from the result set
	            return orderItem;
	        });
	}

	@Override
	public List<Orders> getAll() {
        String query = "SELECT * FROM orders";
        return jdbcTemplate.query(query, (resultSet, rowNum) -> {
            Orders order = new Orders();
            order.setId(resultSet.getLong("id"));
            // Set other order properties from the result set
            return order;
        });
	}
	

}
