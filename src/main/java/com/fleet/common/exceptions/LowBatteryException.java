package com.fleet.common.exceptions;

public class LowBatteryException  extends RuntimeException{
    public LowBatteryException(String errorMessage){
        super(errorMessage);
    }
}
