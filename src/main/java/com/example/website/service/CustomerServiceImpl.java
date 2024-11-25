package com.example.website.service;

import com.example.website.entity.Cart;
import com.example.website.entity.Order;
import com.example.website.entity.Product;
import com.example.website.entity.User;
import com.example.website.repo.CartRepository;
import com.example.website.repo.OrderRepository;
import com.example.website.repo.ProductRepository;
import com.example.website.repo.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class CustomerServiceImpl implements  CustomerService{

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Product> listProducts() {
        // Fetch all available products from the repository
        return productRepository.findAll();
    }

    @Override
    @Transactional
    public Cart addToCart(Long customerId, Long productId, Integer quantity) {
        // Validate customer existence
        User customer = userRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with ID: " + customerId));

        // Validate product existence
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + productId));

        if (quantity <= 0 || quantity > product.getAvailableStock()) {
            throw new IllegalArgumentException("Invalid quantity specified. Stock available: " + product.getAvailableStock());
        }

        // Check if the product is already in the cart
        Optional<Cart> existingCartItem = cartRepository.findByCustomerAndProduct(customer, product);

        if (existingCartItem.isPresent()) {
            Cart cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartRepository.save(cartItem);
        } else {
            // Create a new cart item
            Cart cartItem = new Cart();
            cartItem.setCustomer(customer);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartRepository.save(cartItem);
        }

        // Deduct stock from the product
        product.setAvailableStock(product.getAvailableStock() - quantity);
        productRepository.save(product);

        // Return the updated cart
        return cartRepository.findByCustomerAndProduct(customer, product)
                .orElseThrow(() -> new IllegalStateException("Cart item could not be retrieved."));
    }



    @Override
    @Transactional
    public Order placeOrder(Long customerId) {
        // Validate customer existence
        User customer = userRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with ID: " + customerId));

        // Retrieve all cart items for the customer
        List<Cart> cartItems = cartRepository.findByCustomer(customer);

        if (cartItems.isEmpty()) {
            throw new IllegalArgumentException("Cart is empty. Cannot place order.");
        }

        // Calculate total price
        double totalPrice = cartItems.stream()
                .mapToDouble(cart -> cart.getProduct().getPrice() * cart.getQuantity())
                .sum();

        // Create a new order
        Order order = new Order();
        order.setCustomer(customer);
        order.setTotalPrice(totalPrice);
        order.setOrderDate(new Date());
        order.setStatus("PLACED");
        Order savedOrder = orderRepository.save(order);

        // Clear cart after placing order
        cartRepository.deleteAll(cartItems);

        // Return the saved order
        return savedOrder;
    }
}
