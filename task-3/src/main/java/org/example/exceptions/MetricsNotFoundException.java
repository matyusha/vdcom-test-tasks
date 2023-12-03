package org.example.exceptions;

public class MetricsNotFoundException extends Exception{
    public MetricsNotFoundException(String message) {
        super(message + "Conversion not possible.");
    }
}
