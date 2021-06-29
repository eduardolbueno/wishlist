package com.marketplace.wishlist.controller;

import com.marketplace.wishlist.model.Wish;
import com.marketplace.wishlist.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class WishlistController {

    private final WishlistService wishlistService;

    @PostMapping("/wishlist")
    ResponseEntity<Void> addWish(@RequestBody Wish wish) {
        wishlistService.addWish(wish);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/wishlist/{wishId}")
    ResponseEntity<Void> removeWish(@PathVariable UUID wishId) {
        wishlistService.removeWish(wishId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{customerId}/wishlist")
    ResponseEntity<List<Wish>> getWishlist(@PathVariable UUID customerId) {
        return ResponseEntity.ok(wishlistService.getWishlist(customerId));
    }

    @GetMapping("{customerId}/wishlist/{productId}")
    ResponseEntity<Wish> getWish(@PathVariable UUID customerId, @PathVariable UUID productId) {
        return wishlistService.getWish(customerId, productId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
