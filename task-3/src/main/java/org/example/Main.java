package org.example;

import org.example.data.Proportion;
import org.example.data.Task;
import org.example.exceptions.ProportionFormatException;
import org.example.services.DataService;
import org.example.services.MetricsService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {
        final DataService dataService = new DataService();
        init(dataService);
        final MetricsService metricsService = new MetricsService(dataService);
        metricsService.doTasks().forEach(System.out::println);
    }

    public static void init(DataService dataService) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {
            initData(in, dataService);
            initTasks(in, dataService);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void initData(BufferedReader in, DataService dataService) throws IOException {
        System.out.println("Enter data: ");
        String s;
        while ((s = in.readLine()).length() != 0) {
            try {
                Proportion proportion = new Proportion(s);
                dataService.addProportion(proportion);
            } catch (ProportionFormatException e) {
                System.out.println(s + " is not formatted correctly. Proportion not saved.");
            }
        }
    }

    public static void initTasks(BufferedReader in, DataService dataService) throws IOException {
        System.out.println("Enter tasks: ");
        String s;
        while ((s = in.readLine()).length() != 0) {
            try {
                Task task = new Task(s);
                dataService.addTask(task);
            } catch (ProportionFormatException e) {
                System.out.println(s + " is not formatted correctly. The task will not be processed.");
            }
        }
    }

}