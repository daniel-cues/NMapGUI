package com.uniovi.nmapgui.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="address")
public class Address {
	
	private String address;

    public Address(String address) {
		setAddress(address);
	}
    public Address(){}

	@XmlAttribute(name="addr")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Address [address=" + getAddress() + "]";
	}
    
	

}
