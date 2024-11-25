package com.example.website.service;

import com.example.website.entity.Product;
import com.example.website.entity.Seller;
import com.example.website.repo.ProductRepository;
import com.example.website.repo.SellerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Transactional
public class SellerServiceImpl implements SellerService {
    private final SellerRepository sellerRepository;
    private final ProductRepository productRepository;

    public SellerServiceImpl(SellerRepository sellerRepository, ProductRepository productRepository) {
        this.sellerRepository = sellerRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getSellerProducts(Long sellerId) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new IllegalArgumentException("Seller not found"));
        return productRepository.findBySeller(seller);
    }

    @Override
    public Product addProduct(Long sellerId, Product product) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new IllegalArgumentException("Seller not found"));
        product.setSeller(seller);
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long sellerId, Long productId, Product product) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new IllegalArgumentException("Seller not found"));
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        if (!existingProduct.getSeller().equals(seller)) {
            throw new IllegalArgumentException("Unauthorized action");
        }

        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setQuantity(product.getQuantity());
        existingProduct.setDescription(product.getDescription());

        return productRepository.save(existingProduct);
    }

    @Override
    public void deleteProduct(Long sellerId, Long productId) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new IllegalArgumentException("Seller not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        if (!product.getSeller().equals(seller)) {
            throw new IllegalArgumentException("Unauthorized action");
        }

        productRepository.delete(product);
    }
}
