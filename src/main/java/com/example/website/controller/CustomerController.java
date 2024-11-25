package com.example.website.controller;


import com.example.website.entity.Cart;
import com.example.website.entity.Order;
import com.example.website.entity.Product;
import com.example.website.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> listProducts() {
        return ResponseEntity.ok(customerService.listProducts());
    }

    @PostMapping("/{customerId}/cart/{productId}")
    public ResponseEntity<Cart> addToCart(@PathVariable Long customerId, @PathVariable Long productId, @RequestParam Integer quantity) {
        return ResponseEntity.ok(customerService.addToCart(customerId, productId, quantity));
    }

    @PostMapping("/{customerId}/order")
    public ResponseEntity<Order> placeOrder(@PathVariable Long customerId) {
        return ResponseEntity.ok(customerService.placeOrder(customerId));
    }
}

