package com.example.Bus.Finder.System.exceptionHandling;

public class VehicleAlreadyBookedException extends RuntimeException{
    public VehicleAlreadyBookedException(String message) {
        super(message);
    }
}
