package com.group4.backend.service;

import org.springframework.stereotype.Service;

import com.group4.backend.exception.OrderNotFoundException;
import com.group4.backend.model.Order;
import com.group4.backend.repository.OrderRepository;

@Service
public class OrderServiceImpl {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));
    }

    public Iterable<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order updateOrder(Long orderId, Order updatedOrder) {
        return orderRepository.findById(orderId).map(order -> {
            order.setOrderStatus(updatedOrder.getOrderStatus());
            order.setOrderDateTime(updatedOrder.getOrderDateTime());
            order.setOrderItems(updatedOrder.getOrderItems());
            return orderRepository.save(order);
        }).orElseThrow(() -> new OrderNotFoundException(orderId));
    }

    public boolean deleteOrder(Long orderId) {
        if (orderRepository.existsById(orderId)) {
            orderRepository.deleteById(orderId);
            return true; // Successfully deleted
        }
        return false;
    }

}