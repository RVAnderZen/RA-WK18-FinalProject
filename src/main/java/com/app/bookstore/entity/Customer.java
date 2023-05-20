package com.app.bookstore.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class Customer {
	
    private Long id;
        
    private String firstName;

    private String lastName;

    @Email
    private String email;
    
    private String phone;

    private String address;

    private LocalDate registerDate;

    @JsonIgnoreProperties({"customer","hibernateLazyInitializer", "handler"}) 
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private List<Orders> orders = new ArrayList<Orders>();
    
    public Customer(Long id, List<Orders> orders) {
		super();
		this.id = id;
		this.orders = orders;
	}

	public void addOrder(Orders order) {
        orders.add(order);
    }

	public List<Orders> getOrders() {
		return orders;
	}

	
	public void setOrders(List<Orders> orders) {
		this.orders = orders;
	}

	public Long getId() {
		return id;
	}

	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	public void setId(Long id) {
		this.id = id;
	}
    
}
