package com.example.website.repo;


import com.example.website.entity.Product;
import com.example.website.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findBySeller(Seller seller);
}

