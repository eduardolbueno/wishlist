package com.marketplace.wishlist.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final int httpStatus;

    public BusinessException(String message) {
        super(message);
        this.httpStatus = 500;
    }

    public BusinessException(String message, int httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
