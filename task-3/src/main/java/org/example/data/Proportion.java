package org.example.data;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.example.exceptions.ProportionFormatException;

@Getter
@EqualsAndHashCode
public class Proportion {
    private final MetricValue metricValue1;
    private final MetricValue metricValue2;

    public Proportion(MetricValue metricValue1, MetricValue metricValue2) {
        this.metricValue1 = metricValue1;
        this.metricValue2 = metricValue2;
    }

    public Proportion(String str) throws ProportionFormatException {
        if (!str.matches("\\d+\\.{0,1}\\d*\\s\\S+\\s=\\s\\d+\\.{0,1}\\d*\\s\\S+")) {
            throw new ProportionFormatException();
        }
        String[] arr = str.split("\\s");
        metricValue1 = new MetricValue(Double.parseDouble(arr[0]), arr[1]);
        metricValue2 = new MetricValue(Double.parseDouble(arr[3]), arr[4]);
    }

    @Override
    public String toString() {
        return metricValue1 + " = "+ metricValue2;
    }
}
