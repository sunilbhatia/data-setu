package dev.sunilb.helpers;

import java.io.IOException;
import java.io.InputStream;

public class TestHelpers {

    public static String getResourceStreamAsString(String resourcePath) throws IOException {
        InputStream in = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(resourcePath);

        return new String(in.readAllBytes());

    }
}