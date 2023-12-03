package org.example.services;

import lombok.RequiredArgsConstructor;
import org.example.data.MetricResult;
import org.example.data.MetricValue;
import org.example.data.Proportion;
import org.example.data.Task;
import org.example.exceptions.ConversionNotPossibleException;
import org.example.exceptions.MetricsNotFoundException;

import java.util.*;

@RequiredArgsConstructor
public class MetricsService {

    private final DataService dataService;

    public List<MetricResult> doTasks() {

        return dataService.getTasks().stream()
                .map(task -> {
                    try {
                        final Proportion proportion = doTask(task);
                        return MetricResult.builder()
                                .proportion(proportion)
                                .build();
                    } catch (ConversionNotPossibleException | MetricsNotFoundException e) {
                        return MetricResult.builder()
                                .message(e.getMessage())
                                .build();
                    }
                })
                .toList();
    }

    public Proportion doTask(Task task) throws ConversionNotPossibleException, MetricsNotFoundException {
        double result = findMetricValue(task, findWays(task));
        return new Proportion(task.getMetricValue(), new MetricValue(result, task.getMetric()));
    }


    private Map<String, String> findWays(Task task) throws MetricsNotFoundException, ConversionNotPossibleException {
        Queue<String> queue = new LinkedList<>();
        Set<String> used = new HashSet<>();
        Map<String, String> prev = new HashMap<>();

        queue.add(task.getMetric());
        if (dataService.getGraph().containsKey(task.getMetric())) {
            used.add(task.getMetric());
        } else {
            throw new MetricsNotFoundException(String.format(
                    "Proportions containing the metric %s not found.", task.getMetric()));
        }
        if (dataService.getGraph().containsKey(task.getMetricValue().getMetric())) {
            used.add(task.getMetric());
        } else {
            throw new MetricsNotFoundException(String.format(
                    "Proportions containing the metric %s not found.", task.getMetricValue().getMetric()));
        }

        while (!queue.isEmpty()) {
            String current = queue.poll();
            for (String next : dataService.getGraph().get(current)) {
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
        while (!current.equals(task.getMetric())) {
            String next = prev.get(current);
            String finalCurrent = current;
            Proportion proportion = dataService.getProportions().stream()
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
            current = next;
        }
        return result;
    }
}
