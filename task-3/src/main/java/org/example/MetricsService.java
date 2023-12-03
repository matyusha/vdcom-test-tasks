package org.example;

import org.example.exceptions.ConversionNotPossibleException;
import org.example.exceptions.MetricsNotFoundException;

import java.util.*;

import static org.example.DataService.*;

public class MetricsService {

    public void doTasks() {
        for (Task task : tasks) {
            try {
                Proportion proportion = doTask(task);
                System.out.println(proportion);
            } catch (ConversionNotPossibleException | MetricsNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private Proportion doTask(Task task) throws ConversionNotPossibleException, MetricsNotFoundException {
        double result = findMetricValue(task, findWays(task));
        return new Proportion(task.getMetricValue(), new MetricValue(result, task.getMetric()));
    }


    private Map<String, String> findWays(Task task) throws MetricsNotFoundException, ConversionNotPossibleException {
        Queue<String> queue = new LinkedList<>();
        Set<String> used = new HashSet<>();
        Map<String, String> prev = new HashMap<>();

        queue.add(task.getMetric());
        if (DataService.graph.containsKey(task.getMetric())) {
            used.add(task.getMetric());
        } else {
            throw new MetricsNotFoundException("Proportions containing the metric " + task.getMetric() + " not found. ");
        }
        if (DataService.graph.containsKey(task.getMetricValue().getMetric())) {
            used.add(task.getMetric());
        } else {
            throw new MetricsNotFoundException("Proportions containing the metric " + task.getMetricValue().getMetric() + " not found. ");
        }

        while (!queue.isEmpty()) {
            String current = queue.poll();
            for (String next : DataService.graph.get(current)) {
                if (!used.contains(next)) {
                    queue.add(next);
                    used.add(next);
                    prev.put(next, current);
                }
            }
        }

        if (!used.contains(task.getMetricValue().getMetric())) {
            throw new ConversionNotPossibleException();
        } else {
            return prev;
        }
    }

    private double findMetricValue(Task task, Map<String, String> prev) {
        String current = task.getMetricValue().getMetric();
        double result = task.getMetricValue().getValue();
        while (true) {
            String next = prev.get(current);
            String finalCurrent = current;
            Proportion proportion = DataService.proportions.stream()
                    .filter(p -> {
                        String metric1 = p.getMetricValue1().getMetric();
                        String metric2 = p.getMetricValue2().getMetric();
                        return metric1.equals(finalCurrent) && metric2.equals(next) ||
                                metric1.equals(next) && metric2.equals(finalCurrent);
                    })
                    .findFirst().orElseThrow();
            if (current.equals(proportion.getMetricValue1().getMetric())) {
                result = result * proportion.getMetricValue2().getValue() / proportion.getMetricValue1().getValue();
            } else {
                result = result * proportion.getMetricValue1().getValue() / proportion.getMetricValue2().getValue();
            }
            if (next.equals(task.getMetric())) {
                break;
            } else {
                current = next;
            }
        }
        return result;
    }
}
