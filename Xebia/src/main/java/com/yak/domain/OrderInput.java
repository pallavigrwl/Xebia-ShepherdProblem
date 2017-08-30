package com.yak.domain;

public class OrderInput {
	
	private String customer;
	private Stock order;
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public Stock getOrder() {
		return order;
	}
	public void setOrder(Stock order) {
		this.order = order;
	}

}
