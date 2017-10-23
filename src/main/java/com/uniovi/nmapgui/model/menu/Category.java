package com.uniovi.nmapgui.model.menu;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Category {
	
	private List<Category> subcategories = new ArrayList<>();
	private List<Option> options = new ArrayList<>();
	private List<Option> categoryOptions = new ArrayList<>();
	private List<Select> selects = new ArrayList<>();
	private List<ServerContent> serverContents= new ArrayList<>();

	private String title;
	private String id;

	@XmlElement(name="category")
	public List<Category> getSubcategories() {
		return subcategories;
	}

	public void setSubcategories(List<Category> subcategories) {
		this.subcategories = subcategories;
	}

	@XmlElement(name="option")
	public List<Option> getOptions() {
		return options;
	}

	public void setOptions(List<Option> options) {
		this.options = options;
	}

	@XmlAttribute(name="title")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	@XmlAttribute(name="id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@XmlElement(name="selection")
	public List<Select> getSelects() {
		return selects;
	}

	public void setSelects(List<Select> selects) {
		this.selects = selects;
	}

	@XmlElement(name="categoryOption")
	public List<Option> getCategoryOptions() {
		return categoryOptions;
	}

	public void setCategoryOptions(List<Option> categoryOptions) {
		this.categoryOptions = categoryOptions;
	}

	@XmlElement(name="serverContent")
	public List<ServerContent> getServerContents() {
		return serverContents;
	}

	public void setServerContents(List<ServerContent> serverContents) {
		this.serverContents = serverContents;
	}

}
