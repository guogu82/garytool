package com.gary.garytool.lib.fancyindexer.domain;

import java.util.List;

import com.gary.garytool.lib.fancyindexer.utils.PinyinUtil;

public class CarBrand implements Comparable<CarBrand> {

	private String name;
	private String pinyin;

	private List<GoodMan> brands;


	public CarBrand(String name, List<GoodMan> brands) {
		super();
		this.name = name;
		this.pinyin = PinyinUtil.getPinyin(name);
		this.brands=brands;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPinyin() {
		return pinyin;
	}
	
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public List<GoodMan> getBrands() {
		return brands;
	}

	public void setBrands(List<GoodMan> brands) {
		this.brands = brands;
	}


	@Override
	public int compareTo(CarBrand another) {
		return pinyin.compareTo(another.pinyin);
	}

}
