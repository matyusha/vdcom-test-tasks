package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        int n;
        try (Scanner scanner = new Scanner(System.in)) {
            n = scanner.nextInt();
        }
        Path path = Paths.get("Output.txt");
        try {
            Files.write(path, List.of("0"), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        doTask(n);
    }

    public static void doTask(int n) {
        Semaphore semaphore = new Semaphore(1);
        Thread thread = new Thread(() -> rewriteFile(n, semaphore));
        Thread thread2 = new Thread(() -> rewriteFile(n, semaphore));
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(thread);
        executorService.submit(thread2);
        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
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

    static void rewriteFile(int n, Semaphore semaphore) {
        System.out.println(Thread.currentThread().getName() + " started");
        int k;
        while (true) {
            try {
                semaphore.acquire();
                try (Scanner scanner = new Scanner(new File("Output.txt"))) {
                    k = scanner.nextInt();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }

                if(k==n){
                    semaphore.release();
                    break;
                }

                System.out.println("old value: " + k + " new value: " + ++k + " " + Thread.currentThread().getName());
                try (FileWriter writer = new FileWriter("Output.txt")){
                    writer.write(String.valueOf(k));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                semaphore.release();
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}