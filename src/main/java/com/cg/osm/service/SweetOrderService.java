package com.cg.osm.service;

import java.util.List;
import java.util.Optional;

import com.cg.osm.entity.Product;
import com.cg.osm.entity.SweetOrder;
import com.cg.osm.error.CustomerNotFoundException;
import com.cg.osm.error.ProductNotFoundException;
import com.cg.osm.error.SweetOrderNotFoundException;

public interface SweetOrderService {
	public SweetOrder addSweetOrder(SweetOrder sweetOrder) throws ProductNotFoundException, CustomerNotFoundException;

	public String cancelSweetOrder(int sweetOrderId) throws SweetOrderNotFoundException;

	public List<SweetOrder> ShowAllSweetOrder() throws SweetOrderNotFoundException;

	public double calculateTotalCost(List<Product> prodlist);

	public List<SweetOrder> findOrdersByCustomerId(int customerId)
			throws CustomerNotFoundException, SweetOrderNotFoundException;

	public Optional<SweetOrder> findOrderById(int orderId) throws SweetOrderNotFoundException;
}