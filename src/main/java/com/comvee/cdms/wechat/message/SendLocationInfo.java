package com.comvee.cdms.wechat.message;

public class SendLocationInfo {
    //X坐标信息
    private double location_X;
    //Y坐标信息
    private double location_Y;
    //精度，可理解为精度或者比例尺、越精细的话 scale越高
    private int scale;
    //地理位置的字符串信息
    private String label;
    //朋友圈POI的名字，可能为空
    private String poiname;
    
	public double getLocation_X() {
		return location_X;
	}
	public void setLocation_X(double location_X) {
		this.location_X = location_X;
	}
	public double getLocation_Y() {
		return location_Y;
	}
	public void setLocation_Y(double location_Y) {
		this.location_Y = location_Y;
	}
	public int getScale() {
		return scale;
	}
	public void setScale(int scale) {
		this.scale = scale;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getPoiname() {
		return poiname;
	}
	public void setPoiname(String poiname) {
		this.poiname = poiname;
	}
}
