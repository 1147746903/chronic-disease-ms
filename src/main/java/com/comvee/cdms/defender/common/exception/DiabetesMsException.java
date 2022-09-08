package com.comvee.cdms.defender.common.exception;



public class DiabetesMsException extends ExceptionBase{
	private static final long serialVersionUID = 1L;
	public DiabetesMsException(String code, String msg, String exception){
		super(code,msg,exception);
	}
	public DiabetesMsException(String code, String msg, String other, String exception){
		super(code,msg,other,exception);
	}
	public DiabetesMsException(String code, String msg, String other, Throwable e){
		super(code,msg,other,e);
	}
	public DiabetesMsException(String code, String msg, Throwable e){
		super(code,msg,e);
	}	
	
}
