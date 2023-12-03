package org.example;

import org.example.exceptions.ProportionFormatException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class DataService {
    final static Map<String, Set<String>> graph = new HashMap<>();
    final static Set<Proportion> proportions = new HashSet<>();
    final static Set<Task> tasks = new LinkedHashSet<>();

    public static void init() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {
            initData(in);
            initTasks(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void initData(BufferedReader in) throws IOException {
        System.out.println("Enter data: ");
        String s;
        while ((s = in.readLine()).length() != 0) {
            try {
                Proportion proportion = new Proportion(s);
                proportions.add(proportion);

                graph.putIfAbsent(proportion.getMetricValue1().getMetric(), new HashSet<>());
                graph.get(proportion.getMetricValue1().getMetric())
                        .add(proportion.getMetricValue2().getMetric());

                graph.putIfAbsent(proportion.getMetricValue2().getMetric(), new HashSet<>());
                graph.get(proportion.getMetricValue2().getMetric())
                        .add(proportion.getMetricValue1().getMetric());
            } catch (ProportionFormatException e) {
                System.out.println(s + " is not formatted correctly. Proportion not saved.");
            }
        }
    }

    public static void initTasks(BufferedReader in) throws IOException {
        System.out.println("Enter tasks: ");
        String s;
        while ((s = in.readLine()).length() != 0) {
            try {
                Task task = new Task(s);
                tasks.add(task);
            } catch (ProportionFormatException e) {
                System.out.println(s + " is not formatted correctly. The task will not be processed.");
            }
        }
    }
}
