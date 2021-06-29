package com.marketplace.wishlist.repository;

import com.marketplace.wishlist.model.Wish;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface WishlistRepository extends MongoRepository<Wish, UUID> {

    List<Wish> findByCustomerId(UUID customerId);

    Integer countByCustomerId(UUID customerId);

    Wish findByCustomerIdAndProductId(UUID customerId, UUID productId);

}
