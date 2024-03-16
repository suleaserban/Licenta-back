package com.example.nutriCare.Services;

import com.example.nutriCare.Dtos.CartItemDTO;
import com.example.nutriCare.Dtos.ShoppingCartDTO;
import com.example.nutriCare.Entities.*;
import com.example.nutriCare.Repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ShoppingCartService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    public void addProductToCart(Long userId, Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ShoppingCart shoppingCart = shoppingCartRepository.findShoppingCartByUserId(userId)
                .orElseGet(() -> {
                    ShoppingCart newShoppingCart = new ShoppingCart();
                    newShoppingCart.setUser(user);
                    return newShoppingCart;
                });


        CartItem existingCartItem = shoppingCart.getCartItems().stream()
                .filter(cartItem -> cartItem.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (existingCartItem != null) {

            existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
        } else {

            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setShoppingCart(shoppingCart);
            shoppingCart.getCartItems().add(cartItem);
        }

        shoppingCartRepository.save(shoppingCart);
    }

    public ShoppingCartDTO getCartByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Optional<ShoppingCart> optionalShoppingCart = shoppingCartRepository.findShoppingCartWithItemsSortedByProductId(userId);

        if (!optionalShoppingCart.isPresent()) {

            ShoppingCartDTO emptyCart = new ShoppingCartDTO();
            emptyCart.setUserId(userId);
            emptyCart.setItems(Collections.emptyList());
            emptyCart.setTotal(0D);
            return emptyCart;
        }

        ShoppingCart shoppingCart = optionalShoppingCart.get();
        return mapToDto(shoppingCart);
    }

    private ShoppingCartDTO mapToDto(ShoppingCart shoppingCart) {
        ShoppingCartDTO dto = new ShoppingCartDTO();
        List<CartItemDTO> cartItemDtos = shoppingCart.getCartItems().stream()
                .sorted(Comparator.comparing(item -> item.getProduct().getId()))
                .map(this::mapToDto)
                .collect(Collectors.toList());

        dto.setUserId(shoppingCart.getUser().getId());
        dto.setItems(cartItemDtos);
        dto.setTotal(cartItemDtos.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum());

        return dto;
    }

    @Transactional
    public void removeProductFromCart(Long userId, Long productId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findShoppingCartWithItemsSortedByProductId(userId)
                .orElse(new ShoppingCart());

        CartItem cartItem = shoppingCart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not found in cart"));


        shoppingCart.getCartItems().remove(cartItem);
        shoppingCartRepository.save(shoppingCart);


    }

    @Transactional
    public void changeProductQuantity(Long userId, Long productId, int change) {
        ShoppingCart shoppingCart = shoppingCartRepository.findShoppingCartWithItemsSortedByProductId(userId)
                .orElseThrow(() -> new RuntimeException("Shopping cart not found"));

        CartItem cartItem = shoppingCart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not found in cart"));

        int newQuantity = cartItem.getQuantity() + change;
        if (newQuantity <= 0) {
            shoppingCart.getCartItems().remove(cartItem);
        } else {
            cartItem.setQuantity(newQuantity);
        }

        shoppingCartRepository.save(shoppingCart);
    }

    private CartItemDTO mapToDto(CartItem cartItem) {
        CartItemDTO dto = new CartItemDTO();
        Product product = cartItem.getProduct();
        dto.setProductId(product.getId());
        dto.setProductName(product.getNume());
        dto.setQuantity(cartItem.getQuantity());
        dto.setPrice(product.getPret());


        return dto;
    }



}
