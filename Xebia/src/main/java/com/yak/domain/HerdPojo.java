package com.yak.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="herd")
@XmlAccessorType (XmlAccessType.FIELD)
public class HerdPojo {

	@XmlElement(name="labyak")
	private List<LabYak> labyakList;

	public List<LabYak> getLabyakList() {
		return labyakList;
	}

	public void setLabyakList(List<LabYak> labyakList) {
		this.labyakList = labyakList;
	}

}

