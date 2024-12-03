package jv.supermarket.exceptions;

public class BadAuthRequestException extends RuntimeException {

    public BadAuthRequestException(String message) {
        super(message);
    }

}
