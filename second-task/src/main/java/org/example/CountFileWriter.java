package org.example;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RequiredArgsConstructor
public class CountFileWriter {

    private final Path path;
    private final int maxCount;

    public boolean incrementCount() {

        try {
            int k = Integer.parseInt(Files.readString(path));
            if (k >= maxCount) {
                return true;
            }

            final String message = String.format(
                    "old value: %s new value: %s thread: %s",
                    k, ++k, Thread.currentThread().getName());
            System.out.println(message);

            Files.writeString(path, String.valueOf(k));

            return false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

}
