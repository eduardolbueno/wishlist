# Wishlist

## Building & running
```
mvn clean package && \ 
docker build -t wishlist ./ && \ 
docker-compose up
```

Swagger endpoint: http://localhost:8080/swagger-ui.html

## Integration tests
`mvn verify`
