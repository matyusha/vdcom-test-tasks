package org.example;

import static org.example.DataService.init;

public class Main {

    private final static MetricsService metricService = new MetricsService();

    public static void main(String[] args) {
        init();
        metricService.doTasks();
    }

}