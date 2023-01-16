package com.users.secruity.services;

import com.users.entities.User;
import com.users.entities.Orders;
import com.users.entities.Product;
import com.users.repositories.UserRepository;
import com.users.repositories.OrderRepo;
import com.users.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-orders
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 24/04/22
 * Time: 12.20
 */
@Service
@RequiredArgsConstructor
public class OrderService {
    private final UserRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrderRepo orderRepository;

    public Iterable<User> findAllCustomers() {
        return customerRepository.findAll();
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public List<Orders> findAllOrders() {
        return orderRepository.findAll();
    }
}
