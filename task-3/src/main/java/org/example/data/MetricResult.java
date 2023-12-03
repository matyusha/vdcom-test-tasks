package org.example.data;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MetricResult {
    private Proportion proportion;
    private String message;

    @Override
    public String toString() {
        if (message == null) {
            return proportion.toString();
        }
        return message;
    }
}
