package com.comvee.cdms.machine.common.exception;


import com.comvee.cdms.common.exception.BaseException;

/**
 * 
 * @author 李左河
 *
 */
public class DiabeteslsException extends BaseException {
	private static final long serialVersionUID = 1L;
	public DiabeteslsException(String code, String msg, String exception){
		super(code,msg,exception);
	}
	public DiabeteslsException(String code, String msg, String other, String exception){
		super(code,msg,other,exception);
	}
	public DiabeteslsException(String code, String msg, String other, Throwable e){
		super(code,msg,other,e);
	}
	public DiabeteslsException(String code, String msg, Throwable e){
		super(code,msg,e);
	}	
	
}
