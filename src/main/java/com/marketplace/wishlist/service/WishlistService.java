package com.marketplace.wishlist.service;

import com.marketplace.wishlist.exception.BusinessException;
import com.marketplace.wishlist.model.Wish;
import com.marketplace.wishlist.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;

    public List<Wish> getWishlist(UUID customerId) {
        return wishlistRepository.findByCustomerId(customerId);
    }

    public void addWish(Wish wish) {
        if (wish.getId() == null) {
            wish.setId(UUID.randomUUID());
        }
        if (wishlistRepository.countByCustomerId(wish.getCustomerId()) == 20) {
            throw new BusinessException("Wishlist can't contain more than 20 items", HttpStatus.BAD_REQUEST.value());
        }
        wishlistRepository.save(wish);
    }

    public void removeWish(UUID wishId) {
        wishlistRepository.deleteById(wishId);
    }

    public Optional<Wish> getWish(UUID customerId, UUID productId) {
        return Optional.ofNullable(wishlistRepository.findByCustomerIdAndProductId(customerId, productId));
    }
}
