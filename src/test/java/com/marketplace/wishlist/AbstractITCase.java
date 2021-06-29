package com.marketplace.wishlist;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

@ActiveProfiles("it")
public class AbstractITCase {

    static final MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));
    static final ObjectMapper mapper;

    static {
        initTestContainer();
        mapper = new ObjectMapper();
    }

    static private void initTestContainer() {
        mongoDBContainer.start();
    }

    @DynamicPropertySource
    static void dynamicPropertyRegistry(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @SneakyThrows
    protected static String toJsonString(Object obj) {
        return mapper.writeValueAsString(obj);
    }
}
