FROM azul/zulu-openjdk:11

EXPOSE 8080 8081

COPY target/wishlist*.jar wishlist.jar

ENTRYPOINT ["java","-jar","/wishlist.jar"]