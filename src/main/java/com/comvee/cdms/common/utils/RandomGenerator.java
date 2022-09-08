package com.comvee.cdms.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;



/**
 * 该类提供了生成随机数的相关功能。
 *
 * @author 陈成
 * @version 1.0.0 [2013.03.19]
 */
public final class RandomGenerator {
	
	
	
	/**
	 * Random 对象。
	 */
	private Random random = null;
	
	
	
	/**
	 * 构造一个 RandomGenerator 对象。
	 */
	public RandomGenerator() {
		random = new Random();
	}
	
	/**
	 * 根据给定的 Random 对象，构造一个 RandomGenerator 对象。
	 *
	 * @param random Random 对象。
	 * @throws IllegalArgumentException 参数异常。
	 */
	public RandomGenerator(Random random) {
		if (random == null) {
			throw new IllegalArgumentException("random 参数异常");
		}
		this.random = random;
	}
	
	
	
	/**
	 * 生成一个随机布尔值。
	 *
	 * @return 生成的随机布尔值。
	 */
	public boolean nextBoolean() {
		return random.nextBoolean();
	}
	
	/**
	 * 生成一个指定范围（包括该范围）内的随机数。
	 *
	 * @param start 随机数的起始范围。
	 * @param end 随机数的结束范围。
	 * @return 生成的随机数。
	 * @throws IllegalArgumentException 参数异常。
	 */
	public int nextNumber(int start, int end) {
		if (end < start) {
			throw new IllegalArgumentException("end 参数异常");
		}
		int i = random.nextInt(end - start + 1);
		return i + start;
	}
	
	/**
	 * 生成一组指定长度的，由数字组成的随机字符串。
	 *
	 * @param length 生成的随机字符串的长度。
	 * @return 生成的随机字符串。
	 * @throws IllegalArgumentException 参数异常。
	 */
	public String nextNumberString(int length) {
		if (length < 0) {
			throw new IllegalArgumentException("length 参数异常");
		}
		StringBuilder stringBuilder = new StringBuilder();
		for(int i = 0; i < length; i++) {
			stringBuilder.append(random.nextInt(10));
		}
		return stringBuilder.toString();
	}
	
	/**
	 * 生成一组指定长度的，由大小写英文字母组成的随机字符串。
	 *
	 * @param length 生成的随机字符串的长度。
	 * @return 生成的随机字符串。
	 * @throws IllegalArgumentException 参数异常。
	 */
	public String nextLetter(int length) {
		if (length < 0) {
			throw new IllegalArgumentException("length 参数异常");
		}
		StringBuilder stringBuilder = new StringBuilder();
		for(int i = 0; i < length; i++) {
			int type = nextNumber(0, 1);
			switch(type) {
				case 0:
					stringBuilder.append((char)nextNumber(65, 90));
					break;
				case 1:
					stringBuilder.append((char)nextNumber(97, 122));
					break;
				default:break;
			}
		}
		return stringBuilder.toString();
	}
	
	/**
	 * 生成一组指定长度的，由大写英文字母组成的随机字符串。
	 *
	 * @param length 生成的随机字符串的长度。
	 * @return 生成的随机字符串。
	 * @throws IllegalArgumentException 参数异常。
	 */
	public String nextLetterToUpper(int length) {
		if (length < 0) {
			throw new IllegalArgumentException("length 参数异常");
		}
		StringBuilder stringBuilder = new StringBuilder();
		for(int i = 0; i < length; i++) {
			stringBuilder.append((char)nextNumber(65, 90));
		}
		return stringBuilder.toString();
	}
	
	/**
	 * 生成一组指定长度的，由小写英文字母组成的随机字符串。
	 *
	 * @param length 生成的随机字符串的长度。
	 * @return 生成的随机字符串。
	 * @throws IllegalArgumentException 参数异常。
	 */
	public String nextLetterToLower(int length) {
		if (length < 0) {
			throw new IllegalArgumentException("length 参数异常");
		}
		StringBuilder stringBuilder = new StringBuilder();
		for(int i = 0; i < length; i++) {
			stringBuilder.append((char)nextNumber(97, 122));
		}
		return stringBuilder.toString();
	}
	
	/**
	 * 生成一组指定长度的，由数字及大小写英文字母组成的随机字符串。
	 *
	 * @param length 生成的随机字符串的长度。
	 * @return 生成的随机字符串。
	 * @throws IllegalArgumentException 参数异常。
	 */
	public String nextNumberLetter(int length) {
		if (length < 0) {
			throw new IllegalArgumentException("length 参数异常");
		}
		StringBuilder stringBuilder = new StringBuilder();
		for(int i = 0; i < length; i++) {
			int type = nextNumber(0, 2);
			switch(type) {
				case 0:
					stringBuilder.append(nextNumber(0, 9));
					break;
				case 1:
					stringBuilder.append((char)nextNumber(65, 90));
					break;
				case 2:
					stringBuilder.append((char)nextNumber(97, 122));
					break;
				default:break;
			}
		}
		return stringBuilder.toString();
	}
	
	/**
	 * 生成一组指定长度的，由数字及大写英文字母组成的随机字符串。
	 *
	 * @param length 生成的随机字符串的长度。
	 * @return 生成的随机字符串。
	 * @throws IllegalArgumentException 参数异常。
	 */
	public String nextNumberLetterToUpper(int length) {
		if (length < 0) {
			throw new IllegalArgumentException("length 参数异常");
		}
		StringBuilder stringBuilder = new StringBuilder();
		for(int i = 0; i < length; i++) {
			int type = nextNumber(0, 1);
			switch(type) {
				case 0:
					stringBuilder.append(nextNumber(0, 9));
					break;
				case 1:
					stringBuilder.append((char)nextNumber(65, 90));
					break;
				default:break;
			}
		}
		return stringBuilder.toString();
	}
	
	/**
	 * 生成一组指定长度的，由数字及小写英文字母组成的随机字符串。
	 *
	 * @param length 生成的随机字符串的长度。
	 * @return 生成的随机字符串。
	 * @throws IllegalArgumentException 参数异常。
	 */
	public String nextNumberLetterToLower(int length) {
		if (length < 0) {
			throw new IllegalArgumentException("length 参数异常");
		}
		StringBuilder stringBuilder = new StringBuilder();
		for(int i = 0; i < length; i++) {
			int type = nextNumber(0, 1);
			switch(type) {
				case 0:
					stringBuilder.append(nextNumber(0, 9));
					break;
				case 1:
					stringBuilder.append((char)nextNumber(97, 122));
					break;
				default:break;
			}
		}
		return stringBuilder.toString();
	}
	
	public String sequenceNo(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(new Date())+nextNumberString(6);
	}
	
	
}