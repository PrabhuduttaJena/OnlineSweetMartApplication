package com.cg.osm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Entity
public class Category {
	@Id
	private Integer categoryId;
	@Column(unique = true)
	@NotEmpty(message = "Category name cannot be empty")
    private String name;
	
	public Category() {
		super();
	}

	public Category(Integer categoryId, @NotNull(message = "Category name cannot be null") String name) {
		this.categoryId = categoryId;
		this.name = name;
	}
	
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return categoryId+" "+name;
	}
	
	

	
	
}