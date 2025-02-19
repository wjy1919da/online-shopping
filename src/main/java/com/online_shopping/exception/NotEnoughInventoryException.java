package com.online_shopping.exception;

public class NotEnoughInventoryException extends RuntimeException {
    public NotEnoughInventoryException() {

        super("Not enough inventory for this product.");
    }
}