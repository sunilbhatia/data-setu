package dev.sunilb.datasetu.exceptions;

import com.fasterxml.jackson.databind.JsonNode;

public class DataSetuAPIThrottledException extends RuntimeException {
    public DataSetuAPIThrottledException(String message) {
        super(message);
    }
}
