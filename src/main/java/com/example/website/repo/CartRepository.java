package com.example.website.repo;

import com.example.website.entity.Cart;
import com.example.website.entity.Product;
import com.example.website.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByCustomer(User customer);
    Optional<Cart> findByCustomerAndProduct(User customer, Product product);
}
