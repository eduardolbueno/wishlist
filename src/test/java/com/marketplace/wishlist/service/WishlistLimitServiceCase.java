package com.marketplace.wishlist.service;

import com.marketplace.wishlist.AbstractITCase;
import com.marketplace.wishlist.exception.BusinessException;
import com.marketplace.wishlist.model.Wish;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class WishlistLimitServiceCase extends AbstractITCase {

    @Autowired
    WishlistService wishlistService;

    @Test
    public void insertAndFetchWishSuccess() throws BusinessException {
        UUID customerId = UUID.randomUUID();
        for (int i = 0; i < 20; i++) {
            wishlistService.addWish(Wish.builder()
                    .customerId(customerId)
                    .productId(UUID.randomUUID())
                    .build());
        }

        assertThrows(BusinessException.class, () ->
                wishlistService.addWish(Wish.builder()
                        .customerId(customerId)
                        .productId(UUID.randomUUID())
                        .build()));
    }

}
