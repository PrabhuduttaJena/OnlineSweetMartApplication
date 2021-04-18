package com.cg.osm.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class SweetOrder {
	@Id
	private Integer orderId;
	@Column(name = "TotalCost")
	private double totalCost;
	@Column(name = "Date")
	private LocalDate date;
	@JsonBackReference
	@ManyToOne(targetEntity = Customer.class)
	@JoinColumn(name = "customer_userId")
	private Customer customer;
	@ManyToMany
	@JoinTable(name = "Orders_Product", joinColumns = {
			@JoinColumn(name = "sweet_order_orderid") }, inverseJoinColumns = {
					@JoinColumn(name = "prod_list_prodid") })
	private List<Product> prodList;

	public SweetOrder() {

	}

	public SweetOrder(int orderId, List<Product> prodList) {
		this.orderId = orderId;
		this.prodList = prodList;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<Product> getProdList() {
		return prodList;
	}

	public void setProdList(List<Product> prodList) {
		this.prodList = prodList;
	}

	@Override
	public String toString() {
		return "SweetOrder [orderId=" + orderId + ", totalCost=" + totalCost + ", date=" + date + ", customer="
				+ customer.getUsername() + ", prodList=" + prodList + "]";
	}

}