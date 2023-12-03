package org.example.data;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.example.exceptions.ProportionFormatException;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Task {
    private final MetricValue metricValue;
    private final String metric;


    public Task(String str) throws ProportionFormatException {
        if (!str.matches("\\d+\\.{0,1}\\d*\\s\\S+\\s=\\s\\?\\s\\S+")) {
            throw new ProportionFormatException();
        }
        String[] arr = str.split(" ");
        metricValue = new MetricValue(Double.parseDouble(arr[0]), arr[1]);
        metric = arr[4];
    }

    @Override
    public String toString() {
        return metricValue + " = ? " + metric;
    }
}
