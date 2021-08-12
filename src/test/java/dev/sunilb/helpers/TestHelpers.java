package dev.sunilb.helpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public class TestHelpers {

    public static String getResourceStreamAsString(String resourcePath) throws IOException {
        InputStream in = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(resourcePath);

        return new String(in.readAllBytes());

    }

    public static boolean isJsonEqual(String compareJson, String withJson) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(compareJson).equals(mapper.readTree(withJson));
    }
}