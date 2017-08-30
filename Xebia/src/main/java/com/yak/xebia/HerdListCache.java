package com.yak.xebia;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.yak.domain.HerdPojo;
import com.yak.domain.LabYak;
import com.yak.domain.Stock;

@Component
public class HerdListCache {
	
	private List<LabYak> yakCache;
	private Stock stock;
	
	public void initializeCache(HerdPojo pojo) throws Exception {
		if(this.yakCache == null) {
			yakCache = new ArrayList<LabYak>();
		}
		this.yakCache.addAll(pojo.getLabyakList());
	}

	public List<LabYak> getYakCache() {
		return yakCache;
	}

	public void addYakToCache(LabYak yak) {
		yakCache.add(yak);
	}
	
	public void setYakCache(List<LabYak> yakCache) {
		this.yakCache = yakCache;
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

}
