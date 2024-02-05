package com.example.nutriCare.Services;

import com.example.nutriCare.Dtos.CartItemDTO;
import com.example.nutriCare.Dtos.ShoppingCartDTO;
import com.example.nutriCare.Entities.CartItem;
import com.example.nutriCare.Entities.Product;
import com.example.nutriCare.Entities.ShoppingCart;
import com.example.nutriCare.Entities.User;
import com.example.nutriCare.Repositories.CartItemRepository;
import com.example.nutriCare.Repositories.ProductRepository;
import com.example.nutriCare.Repositories.ShoppingCartRepository;
import com.example.nutriCare.Repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Transactional
    public void addProductToCart(Long userId, Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId)
                .orElse(new ShoppingCart());

        if (shoppingCart.getId() == null) {
            shoppingCart.setUser(user);
        }


        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        cartItem.setShoppingCart(shoppingCart);

        shoppingCart.getCartItems().add(cartItem);

        shoppingCartRepository.save(shoppingCart);
    }

    public ShoppingCartDTO getCartByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Shopping cart not found"));

        return mapToDto(shoppingCart);
    }

    private ShoppingCartDTO mapToDto(ShoppingCart shoppingCart) {
        ShoppingCartDTO dto = new ShoppingCartDTO();
        List<CartItemDTO> cartItemDtos = shoppingCart.getCartItems().stream()
                .map(this::mapToDto)
                .toList();

        dto.setUserId(shoppingCart.getUser().getId());
        dto.setItems(cartItemDtos);
        dto.setTotal(cartItemDtos.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum());

        return dto;
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
