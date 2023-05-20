package com.app.bookstore.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class OrderItem {
    private Long id;
    
    private Book book;

    private int quantity;
    
    @JsonIgnoreProperties({"orderItems","hibernateLazyInitializer", "handler"}) 
    private Orders order;
    
    
    @Enumerated(EnumType.STRING)
    private OrderItemStatus orderStatus = OrderItemStatus.ORDERED;

}
