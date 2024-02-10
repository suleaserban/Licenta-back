package com.example.nutriCare.Controllers;

import com.example.nutriCare.Dtos.ShoppingCartDTO;
import com.example.nutriCare.Services.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class ShoppingCartController {


    @Autowired
    ShoppingCartService shoppingCartService;

    @PostMapping("/add")
    public String addProductToCart(@RequestParam Long userId, @RequestParam Long productId, @RequestParam int quantity) {
        shoppingCartService.addProductToCart(userId, productId, quantity);
        return "Product added to cart successfully";
    }

    @GetMapping("/get")
    public ResponseEntity<ShoppingCartDTO> getCartByUserId(@RequestParam Long userId) {
        ShoppingCartDTO shoppingCartDto = shoppingCartService.getCartByUserId(userId);
        return ResponseEntity.ok(shoppingCartDto);
    }

    @PostMapping("/remove")
    public ResponseEntity<String> removeProductFromCart(@RequestParam Long userId, @RequestParam Long productId) {
        shoppingCartService.removeProductFromCart(userId, productId);
        return ResponseEntity.ok("Product removed from cart successfully");
    }

    @PostMapping("/changeQuantity")
    public ResponseEntity<String> changeProductQuantity(@RequestParam Long userId, @RequestParam Long productId, @RequestParam int change) {
        shoppingCartService.changeProductQuantity(userId, productId, change);
        return ResponseEntity.ok("Product quantity updated successfully");
    }
}
