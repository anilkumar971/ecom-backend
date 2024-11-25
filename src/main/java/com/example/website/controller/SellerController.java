package com.example.website.controller;


import com.example.website.entity.Product;
import com.example.website.service.SellerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sellers")
public class SellerController {

    private final SellerService sellerService;

    public SellerController(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @GetMapping("/{sellerId}/products")
    public ResponseEntity<List<Product>> getSellerProducts(@PathVariable Long sellerId) {
        return ResponseEntity.ok(sellerService.getSellerProducts(sellerId));
    }

    @PostMapping("/{sellerId}/products")
    public ResponseEntity<Product> addProduct(@PathVariable Long sellerId, @RequestBody Product product) {
        return ResponseEntity.ok(sellerService.addProduct(sellerId, product));
    }

    @PutMapping("/{sellerId}/products/{productId}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long sellerId,
            @PathVariable Long productId,
            @RequestBody Product product) {
        return ResponseEntity.ok(sellerService.updateProduct(sellerId, productId, product));
    }

    @DeleteMapping("/{sellerId}/products/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long sellerId, @PathVariable Long productId) {
        sellerService.deleteProduct(sellerId, productId);
        return ResponseEntity.noContent().build();
    }
}
