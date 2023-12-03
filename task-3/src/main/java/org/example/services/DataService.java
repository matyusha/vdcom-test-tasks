package org.example.services;

import lombok.Getter;
import org.example.data.Proportion;
import org.example.data.Task;
import java.util.*;

@Getter
public class DataService {

    final Map<String, Set<String>> graph = new HashMap<>();
    final Set<Proportion> proportions = new HashSet<>();
    final Set<Task> tasks = new LinkedHashSet<>();

    public void addProportion(Proportion proportion) {

        proportions.add(proportion);

        graph.putIfAbsent(proportion.getMetricValue1().getMetric(), new HashSet<>());
        graph.get(proportion.getMetricValue1().getMetric())
                .add(proportion.getMetricValue2().getMetric());

        graph.putIfAbsent(proportion.getMetricValue2().getMetric(), new HashSet<>());
        graph.get(proportion.getMetricValue2().getMetric())
                .add(proportion.getMetricValue1().getMetric());

    }

    public void addTask(Task task) {
        tasks.add(task);
    }
}
