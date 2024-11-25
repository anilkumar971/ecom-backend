package com.example.website.service;



import com.example.website.entity.Product;

import java.util.List;

public interface SellerService {
    List<Product> getSellerProducts(Long sellerId);
    Product addProduct(Long sellerId, Product product);
    Product updateProduct(Long sellerId, Long productId, Product product);
    void deleteProduct(Long sellerId, Long productId);
}
