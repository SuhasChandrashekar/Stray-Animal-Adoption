package com.suhas.myapp.exception;

public class RescueRecordException extends Exception{
	public RescueRecordException(String message)	{
		super("RescueRecordException- "+message);
	}
	public RescueRecordException(String message, Throwable cause){
		super("RescueRecordException- "+message, cause);
	}
}
