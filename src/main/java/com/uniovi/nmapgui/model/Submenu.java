package com.uniovi.nmapgui.model;

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

public class Submenu {
	
	Set<Category> categories = new HashSet<>();

	@XmlElement(name="categorycategories")
	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}

}
