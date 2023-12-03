package org.example;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MetricValue {
    private double value;
    private String metric;

    @Override
    public String toString() {
        return value + " " + metric;
    }
}
