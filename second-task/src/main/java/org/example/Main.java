package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {

        Path path = Paths.get("Output.txt");
        try {
            Files.writeString(path, "0", StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int n;
        try (Scanner scanner = new Scanner(System.in)) {
            n = scanner.nextInt();
        }

        final Semaphore semaphore = new Semaphore(1);
        final CountFileWriter writer = new CountFileWriter(path, n);
        final RewriteFileTask firstTask = new RewriteFileTask(semaphore, writer);
        final RewriteFileTask secondTask = new RewriteFileTask(semaphore, writer);

        final ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(firstTask);
        executorService.submit(secondTask);
        executorService.shutdown();
        try {
            if (executorService.awaitTermination(1, TimeUnit.MINUTES)) {
                System.out.println("executor successfully terminated");
            } else {
                System.out.println("executor termination timeout");
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            throw new RuntimeException(e);
        }

        try (Scanner scanner = new Scanner(new File("Output.txt"))) {
            System.out.println("File contents: ");
            while (scanner.hasNext()) {
                System.out.println(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}