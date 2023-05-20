package com.app.bookstore.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.app.bookstore.entity.OrderItem;
import com.app.bookstore.entity.OrderItemStatus;
import com.app.bookstore.exception.BookNotfoundException;
import com.app.bookstore.service.BookService;
import com.app.bookstore.service.OrderService;

@Component
public class DefaultOrderItemDao implements OrderItemDao{
	
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    
    @Autowired
    private static BookService bookService;
    
    @Autowired
    private static OrderService orderService;
    
    private static final String SAVE_ORDER_ITEM_SQL = "INSERT INTO order_item (book_id, quantity, order_status) "
            + "VALUES (:bookId, :quantity,:orderStatus)";

    private static final String SAVE_ORDER_ITEM_SQL_WITH_ORDERID = "INSERT INTO order_item (book_id, quantity, order_id, order_status) "
            + "VALUES (:bookId, :quantity, :orderId, :orderStatus)";

    private static final String GET_ORDER_ITEMS_SQL = "SELECT * FROM order_item";
    
    private static final String GET_ORDER_ITEM_BY_ID_SQL = "SELECT * FROM order_item WHERE id = :id";

    private static final class OrderItemRowMapper implements RowMapper<OrderItem> {
        @Override
        public OrderItem mapRow(ResultSet rs, int rowNum) throws SQLException {
            OrderItem orderItem = new OrderItem();
            orderItem.setId(rs.getLong("id"));
            try {
				orderItem.setBook(bookService.getBookById(rs.getLong("book_id")));
			} catch (BookNotfoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            orderItem.setQuantity(rs.getInt("quantity"));
            orderItem.setOrder(orderService.getOrderById(rs.getLong("order_id")));
            orderItem.setOrderStatus(OrderItemStatus.valueOf(rs.getString("order_status")));
            return orderItem;
        }
    }

	@Override
	public OrderItem saveOrderItem(OrderItem orderItem) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("bookId", orderItem.getBook().getId());
        parameters.put("quantity", orderItem.getQuantity());
        if(orderItem.getOrder()!=null) {
        	parameters.put("orderId", orderItem.getOrder().getId());
        }
        parameters.put("orderStatus", orderItem.getOrderStatus().toString());
        if(orderItem.getOrder()==null) {
        	jdbcTemplate.update(SAVE_ORDER_ITEM_SQL, parameters);
        }else {
        	jdbcTemplate.update(SAVE_ORDER_ITEM_SQL_WITH_ORDERID, parameters);
        }
        return orderItem;

	}

	@Override
	public List<OrderItem> getOrderItems() {
        return jdbcTemplate.query(GET_ORDER_ITEMS_SQL, new OrderItemRowMapper());
	}

	@Override
	public OrderItem getOrderItemById(Long id) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", id);
        return jdbcTemplate.queryForObject(GET_ORDER_ITEM_BY_ID_SQL, parameters, new OrderItemRowMapper());

	}

}
