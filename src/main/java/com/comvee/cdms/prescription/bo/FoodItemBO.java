/**
 * @File name:   EohIngredientsItem.java    食材项
 * @Create on:   2017年2月17日
 * @Author   :  占铃树
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 * 
 *
**/
package com.comvee.cdms.prescription.bo;

import java.io.Serializable;

/**
 * @author 占铃树
 */
public class FoodItemBO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	/**
	 * 食材名称
	 */
	private String name;
	/**
	 * 交换倍数
	 */
	private String changeDelta;
	/**
	 * 拼音首字母
	 */
	private String spell;
	/**
	 * 热量（每克的热量）千卡
	 */
	private String heat;
	/**
	 * 一份食物的克数
	 */
	private String gram;
	/**
	 * 图片地址
	 */
	private String picture;
	/**
	 * 添加时间
	 */
	private String insertDt;
	/**
	 * 修改时间
	 */
	private String modifyDt;
	/**
	 * 食材类型谷薯类
	 * 旧版：1001001蔬菜类1001003水果类1001004肉蛋类1001007豆类1001002浆乳类1001012油脂类（烹饪油）1001013油脂类（坚果类）1001014
	 * 新版：根据库里数据动态编号（one_type）
	 */
	private String ingredientsType;
	/**
	 * 总克数
	 */
	private String num; 
	/**
	 * 单位
	 */
	private String unit;

	/**新版食材表字段**/
	/**
	 * 每克所含碳水化合物
 	 */
	private Double carbohydrates;
	/**
	 * 每克所含脂肪
	 */
	private Double totalfats;
	/**
	 * 每克所含蛋白质
	 */
	private Double protein;

	/**
	 * 每克钠含量
	 */
	private Double na;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Double getNa() {
		return na;
	}

	public void setNa(Double na) {
		this.na = na;
	}

	public Double getCarbohydrates() {
		return carbohydrates;
	}

	public void setCarbohydrates(Double carbohydrates) {
		this.carbohydrates = carbohydrates;
	}

	public Double getTotalfats() {
		return totalfats;
	}

	public void setTotalfats(Double totalfats) {
		this.totalfats = totalfats;
	}

	public Double getProtein() {
		return protein;
	}

	public void setProtein(Double protein) {
		this.protein = protein;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getChangeDelta() {
		return changeDelta;
	}

	public void setChangeDelta(String changeDelta) {
		this.changeDelta = changeDelta;
	}

	public String getSpell() {
		return spell;
	}

	public void setSpell(String spell) {
		this.spell = spell;
	}

	public String getHeat() {
		return heat;
	}

	public void setHeat(String heat) {
		this.heat = heat;
	}

	public String getGram() {
		return gram;
	}

	public void setGram(String gram) {
		this.gram = gram;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getInsertDt() {
		return insertDt;
	}

	public void setInsertDt(String insertDt) {
		this.insertDt = insertDt;
	}

	public String getModifyDt() {
		return modifyDt;
	}

	public void setModifyDt(String modifyDt) {
		this.modifyDt = modifyDt;
	}

	public String getIngredientsType() {
		return ingredientsType;
	}

	public void setIngredientsType(String ingredientsType) {
		this.ingredientsType = ingredientsType;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Override
	public String toString() {
		return "FoodItemBO{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", changeDelta='" + changeDelta + '\'' +
				", spell='" + spell + '\'' +
				", heat='" + heat + '\'' +
				", gram='" + gram + '\'' +
				", picture='" + picture + '\'' +
				", insertDt='" + insertDt + '\'' +
				", modifyDt='" + modifyDt + '\'' +
				", ingredientsType='" + ingredientsType + '\'' +
				", num='" + num + '\'' +
				", unit='" + unit + '\'' +
				", carbohydrates=" + carbohydrates +
				", totalfats=" + totalfats +
				", protein=" + protein +
				'}';
	}
}
