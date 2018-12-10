package com.mvcemployee.app.models.entity;

import javax.persistence.Embeddable;

@Embeddable
public class Address {

	private String addressCountry;
	private String addressCity;

	public Address() {
	}
	public String getAddressCountry() {
		return addressCountry;
	}
	public void setAddressCountry(String addressCountry) {
		this.addressCountry = addressCountry;
	}
	public String getAddressCity() {
		return addressCity;
	}
	public void setAddressCity(String addressCity) {
		this.addressCity = addressCity;
	}
	
	

}
