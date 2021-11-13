package com.kingshuk.rest.webservices.restfulwebservices.exception;

public class PostNotFoundException extends RuntimeException {

    public PostNotFoundException(String message) {
        super("Post not found for "+message);
    }
}
