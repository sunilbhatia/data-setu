package dev.sunilb.datasetu.exceptions;

public class DataSetuAccessTokenExpiredException extends RuntimeException {
    public DataSetuAccessTokenExpiredException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
