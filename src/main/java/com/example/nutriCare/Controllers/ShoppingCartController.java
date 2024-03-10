package com.example.nutriCare.Controllers;

import com.example.nutriCare.Dtos.ShoppingCartDTO;
import com.example.nutriCare.Services.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
public class ShoppingCartController {


    @Autowired
    ShoppingCartService shoppingCartService;

    @PostMapping("/add")
    public ResponseEntity<Map<String, String>> addProductToCart(@RequestParam Long userId, @RequestParam Long productId, @RequestParam int quantity) {
        shoppingCartService.addProductToCart(userId, productId, quantity);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Product added to cart successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get")
    public ResponseEntity<ShoppingCartDTO> getCartByUserId(@RequestParam Long userId) {
        ShoppingCartDTO shoppingCartDto = shoppingCartService.getCartByUserId(userId);
        return ResponseEntity.ok(shoppingCartDto);
    }

    @PostMapping("/remove")
    public ResponseEntity<ShoppingCartDTO> removeProductFromCart(@RequestParam Long userId, @RequestParam Long productId) {
        shoppingCartService.removeProductFromCart(userId, productId);
        ShoppingCartDTO updatedCart = shoppingCartService.getCartByUserId(userId);
        return ResponseEntity.ok(updatedCart);
    }

    @PostMapping("/changeQuantity")
    public ResponseEntity<ShoppingCartDTO> changeProductQuantity(@RequestParam Long userId, @RequestParam Long productId, @RequestParam int change) {
        shoppingCartService.changeProductQuantity(userId, productId, change);
        ShoppingCartDTO updatedCart = shoppingCartService.getCartByUserId(userId);
        return ResponseEntity.ok(updatedCart);
    }
}
