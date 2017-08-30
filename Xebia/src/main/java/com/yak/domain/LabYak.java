package com.yak.domain;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;

@XmlRootElement(name="labyak")
public class LabYak {

	private String name;
	private String age;
	private String sex;
	private int ageInDays;
	private boolean isAlive;
	

	@Override
	public String toString() {
		return "LabYak [name=" + name + ", age=" + age + ", sex=" + sex + "]";
	}
	public LabYak() {
		
	}
	public LabYak(String name, String age, String sex) {
		super();
		this.name = name;
		setAge(age);
		this.sex = sex;
	}

	public String getName() {
		return name;
	}

	@XmlAttribute
	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	@XmlAttribute
	public void setAge(String age) {
		this.age = age;
		this.setAgeInDays((int)(Float.parseFloat(age)*100));
	}

	public String getSex() {
		return sex;
	}

	@XmlAttribute
	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getAgeInDays() {
		return ageInDays;
	}
	@JsonIgnore
	public void setAgeInDays(int ageInDays) {
		this.ageInDays = ageInDays;
	}
	public boolean isAlive() {
		isAlive = ageInDays <= 1000;
		return isAlive;
	}
	@JsonIgnore
	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

}
