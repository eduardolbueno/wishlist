package com.marketplace.wishlist.component;


import com.marketplace.wishlist.AbstractITCase;
import com.marketplace.wishlist.model.Wish;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class WishlistCrudITCase extends AbstractITCase {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void insertAndFetchWishSuccess() throws Exception {
        UUID customerId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();
        Wish wish = Wish.builder()
                .customerId(customerId)
                .productId(productId)
                .build();
        mockMvc
                .perform(post("/wishlist")
                .content(toJsonString(wish)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc
                .perform(get("/{customerId}/wishlist/{productId}", customerId, productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(customerId.toString()))
                .andExpect(jsonPath("$.productId").value(productId.toString()));
    }

    @Test
    public void insertAndFetchWishlistSuccess() throws Exception {
        UUID customerId = UUID.randomUUID();
        mockMvc
                .perform(post("/wishlist")
                .content(
                        toJsonString(Wish.builder()
                                .customerId(customerId)
                                .productId(UUID.randomUUID())
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc
                .perform(post("/wishlist")
                .content(
                        toJsonString(Wish.builder()
                                .customerId(customerId)
                                .productId(UUID.randomUUID())
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc
                .perform(get("/{customerId}/wishlist", customerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void insertDeleteFetchWishlistSuccess() throws Exception {
        UUID customerId = UUID.randomUUID();
        UUID wishId = UUID.randomUUID();
        mockMvc
                .perform(post("/wishlist")
                        .content(
                                toJsonString(Wish.builder()
                                        .customerId(customerId)
                                        .productId(UUID.randomUUID())
                                        .build()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc
                .perform(post("/wishlist")
                        .content(
                                toJsonString(Wish.builder()
                                        .id(wishId)
                                        .customerId(customerId)
                                        .productId(UUID.randomUUID())
                                        .build()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc
                .perform(get("/{customerId}/wishlist", customerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        mockMvc
                .perform(delete("/wishlist/{wishId}", wishId))
                .andExpect(status().isNoContent());

        mockMvc
                .perform(get("/{customerId}/wishlist", customerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void fetchRandomWishNotFound() throws Exception {
        mockMvc
                .perform(get("/{customerId}/wishlist/{productId}", UUID.randomUUID(), UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

}
