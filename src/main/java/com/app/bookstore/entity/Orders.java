package com.app.bookstore.entity;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Orders {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    
    @JsonIgnoreProperties({"order","hibernateLazyInitializer", "handler"}) 
    private List<OrderItem> orderItems = new ArrayList<OrderItem>();
    
    private BigDecimal totalAmount = new BigDecimal(0.00);
    
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Customer customer;
    
    private String shippingAddress;
    
    private String billingAddress;
    
    private String paymentMethod = "CASH";
    
    
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.NEW;
    
    private LocalDateTime orderedDate;
    
    public void addOrderItem(OrderItem item) {
        orderItems.add(item);
    }
}
