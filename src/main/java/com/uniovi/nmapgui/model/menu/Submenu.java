package com.uniovi.nmapgui.model.menu;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Submenu {
	
	List<Category> categories = new ArrayList<>();
	String title;
	String img;
	String id;
	boolean tabbed;

	@XmlElement(name="category")
	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	@XmlAttribute(name="title")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@XmlAttribute(name="img")
	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	@XmlAttribute(name="id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@XmlAttribute(name="tabbed")
	public boolean isTabbed() {
		return tabbed;
	}

	public void setTabbed(boolean tabbed) {
		this.tabbed = tabbed;
	}
	
	

}
