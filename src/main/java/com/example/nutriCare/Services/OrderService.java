package com.example.nutriCare.Services;

import com.example.nutriCare.Dtos.OrderDTO;
import com.example.nutriCare.Dtos.OrderDetailsDTO;
import com.example.nutriCare.Dtos.OrderDisplayDTO;
import com.example.nutriCare.Dtos.OrderItemDTO;
import com.example.nutriCare.Entities.*;
import com.example.nutriCare.Repositories.OrderItemRepository;
import com.example.nutriCare.Repositories.OrdersRepository;
import com.example.nutriCare.Repositories.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrdersRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Transactional
    public OrderDTO placeOrder(Long userId, OrderDetailsDTO orderDetailsDTO) {
        ShoppingCart cart = shoppingCartRepository.findShoppingCartByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        String fullAddress = String.format("Telefon: %s, Adresă: %s, Oraș: %s, Județ: %s, Cod poștal: %s",
                orderDetailsDTO.getPhoneNumber(),
                orderDetailsDTO.getAddress(),
                orderDetailsDTO.getCity(),
                orderDetailsDTO.getCounty(),
                orderDetailsDTO.getCodPostal());

        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);
        order.setAddress(fullAddress);
        String paymentMethodString = orderDetailsDTO.getPaymentMethod();
        try {
            PaymentMethod paymentMethod = PaymentMethod.valueOf(paymentMethodString.toUpperCase());
            order.setPaymentMethod(paymentMethod);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid payment method");
        }

        Set<OrderItem> orderItems = cart.getCartItems().stream().map(cartItem -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            return orderItem;
        }).collect(Collectors.toSet());

        order.setOrderItems(orderItems);
        order.setTotal(orderItems.stream()
                .mapToDouble(item -> item.getProduct().getPret() * item.getQuantity())
                .sum());

        Order savedOrder = orderRepository.save(order);
        orderItemRepository.saveAll(orderItems);
        cart.getCartItems().clear();
        shoppingCartRepository.save(cart);


        return convertToOrderDTO(savedOrder);
    }

    private OrderDTO convertToOrderDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setUserId(order.getUser().getId());
        orderDTO.setOrderDate(order.getOrderDate());
        orderDTO.setTotal(order.getTotal());
        List<OrderItemDTO> itemDTOs = order.getOrderItems().stream().map(item -> {
            OrderItemDTO dto = new OrderItemDTO();
            dto.setProductId(item.getProduct().getId());
            dto.setQuantity(item.getQuantity());
            return dto;
        }).collect(Collectors.toList());
        orderDTO.setOrderItems(itemDTOs);
        return orderDTO;
    }

    public List<OrderDisplayDTO> getOrdersByUserId(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);

        List<Order> sortedOrders = orders.stream()
                .sorted(Comparator.comparing(Order::getOrderDate))
                .collect(Collectors.toList());


        return sortedOrders.stream()
                .map(this::convertToOrderDisplayDTO)
                .collect(Collectors.toList());

    }

    public List<OrderDisplayDTO> getAllOrders(){
    List<Order> orders = orderRepository.findAll();
        List<Order> sortedOrders = orders.stream()
                .sorted(Comparator.comparing(Order::getOrderDate))
                .collect(Collectors.toList());


        return sortedOrders.stream()
                .map(this::convertToOrderDisplayDTO)
                .collect(Collectors.toList());
        }

    private OrderDisplayDTO convertToOrderDisplayDTO(Order order) {
        OrderDisplayDTO dto = new OrderDisplayDTO();
        dto.setDate(java.util.Date.from(order.getOrderDate().atZone(ZoneId.systemDefault()).toInstant()));
        dto.setStatus(order.getStatus().toString());
        dto.setTotal(order.getTotal());

        String productNames = order.getOrderItems().stream()
                .map(orderItem -> orderItem.getProduct().getNume() + " x " + orderItem.getQuantity())
                .collect(Collectors.joining(", "));
        dto.setProductNames(productNames);
        dto.setAdress(order.getAddress());
        dto.setId(order.getId());


        return dto;
    }


    public OrderDTO updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(status);
        Order savedOrder = orderRepository.save(order);
        return convertToOrderDTO(savedOrder);
    }

}

