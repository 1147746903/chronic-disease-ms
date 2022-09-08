package com.comvee.cdms.glu.model;
/**
 * 
 * @author 李左河
 *
 */
public class ResultModel implements java.io.Serializable{
    
	private static final long serialVersionUID = 1L;
	
	/**
	 *  是否成功
	 */
	private boolean success = false;
	/**
	 *  提示信息
	 */
	private String msg = "";
	/**
	 * 错误代码
	 */
	private String code=""; 
	/**
	 *  其他信息
	 */
	private Object obj = null;
	
	public ResultModel(String code,String msg,boolean success){
		this.code = code;
		this.msg = msg;
		this.success = success;
	}

	public ResultModel(boolean success){
		String code = "1";
		String msg = "失败";
		if (success == true){
			code = "0";
			msg = "成功";
		}
		this.code = code;
		this.msg = msg;
		this.success = success;
	}

	public ResultModel(){
		this.success=true;
	}
	
    public ResultModel(Object obj){
        this.obj = obj;
        this.code = "0";
        this.msg = "";
        this.success = true;
    }

	public ResultModel(String msg){
		this.code = "0";
		this.msg = msg;
		this.success = true;
	}
    
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
}
