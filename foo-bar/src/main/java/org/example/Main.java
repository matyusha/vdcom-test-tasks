package org.example;

import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        int n;
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter integer value: ");
            while (!scanner.hasNextInt()) {
                scanner.nextLine();
            }
            n = scanner.nextInt();
        }
        thirdDecision(n);
    }

    public static void firstDecision(int n) {
        for (int i = 1; i <= n; i++) {
            StringBuilder s = new StringBuilder();
            if (i % 3 == 0) {
                s.append("Foo");
            }
            if (i % 5 == 0) {
                s.append("Bar");
            }
            if (s.isEmpty()) {
                s.append(i);
            }
            System.out.println(s);
        }
    }

    public static void secondDecision(int n) {
        IntStream.range(1, n + 1)
                .mapToObj(i -> {
                    if (i % 3 == 0) {
                        if (i % 5 == 0) {
                            return "FooBar";
                        } else {
                            return "Foo";
                        }
                    } else if (i % 5 == 0) {
                        return "Bar";
                    } else {
                        return i;
                    }
                })
                .forEach(System.out::println);
    }

    public static void thirdDecision(int n) {
        for (int i = 1; i <= n; i++) {
            switch (i % 15) {
                case 0 -> System.out.println("FooBar");
                case 3, 6, 9, 12 -> System.out.println("Foo");
                case 5, 10 -> System.out.println("Bar");
                default -> System.out.println(i);
            }
        }
    }

}