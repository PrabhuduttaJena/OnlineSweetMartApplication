package com.cg.osm.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity(name = "Product")
public class Product {

	@Id
	private Integer prodid;

	@Column(unique = true)
	@NotEmpty(message = "Product name cannot be empty")
	private String prodName;

	@Column
	@Positive(message = "Product cannot be zero or negative")
	private double prodPrice;

	@ManyToMany(mappedBy = "prodList")
	private List<SweetOrder> sweetorder;

	@Column
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate expDate;

	@ManyToOne
	private Category category;

	public Product() {
		super();
	}

	public Product(Integer prodid, @NotEmpty(message = "Product name cannot be empty") String prodName,
			@Positive(message = "Product cannot be zero or negative") double prodPrice, LocalDate expDate,
			Category category) {
		this.prodid = prodid;
		this.prodName = prodName;
		this.prodPrice = prodPrice;
		this.expDate = expDate;
		this.category = category;
	}

	public int getProdId() {
		return prodid;
	}

	public void setProdId(int prodId) {
		this.prodid = prodId;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public double getProdPrice() {
		return prodPrice;
	}

	public void setProdPrice(double prodPrice) {
		this.prodPrice = prodPrice;
	}

	public LocalDate getExpDate() {
		return expDate;
	}

	public void setExpDate(LocalDate expDate) {
		this.expDate = expDate;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "Product [prodId=" + prodid + ", prodName=" + prodName + ", prodPrice=" + prodPrice + ", expDate="
				+ expDate + "]";
	}

}