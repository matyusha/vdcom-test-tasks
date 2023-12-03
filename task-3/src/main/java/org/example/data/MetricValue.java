package org.example.data;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class MetricValue {
    private double value;
    private String metric;

    @Override
    public String toString() {
        return value + " " + metric;
    }
}
