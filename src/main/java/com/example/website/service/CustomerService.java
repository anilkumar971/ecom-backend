package com.example.website.service;



import com.example.website.entity.Cart;
import com.example.website.entity.Order;
import com.example.website.entity.Product;

import java.util.List;

public interface CustomerService {
    List<Product> listProducts();
    Cart addToCart(Long customerId, Long productId, Integer quantity);
    Order placeOrder(Long customerId);
}
