package dev.sunilb.datasetu.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Date;

public class FileUtils {
    public static void saveResponseStringToFile(String fileName, HttpResponse<String> response) {
        Date d = new Date();
        File output = new File(fileName);
        FileWriter writer = null;
        try {
            writer = new FileWriter(output);
            writer.write(response.body());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
