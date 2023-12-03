package org.example.exceptions;

public class MetricsNotFoundException extends Exception {
    public MetricsNotFoundException(String message) {
        super(String.format("%s Conversion not possible.", message));
    }
}
