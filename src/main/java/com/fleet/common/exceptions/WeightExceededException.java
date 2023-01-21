package com.fleet.common.exceptions;

public class WeightExceededException extends RuntimeException{
    public WeightExceededException(String errorMessage){
        super(errorMessage);
    }
}
