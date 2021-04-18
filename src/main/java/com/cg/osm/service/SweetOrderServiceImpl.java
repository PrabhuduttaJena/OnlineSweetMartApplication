package com.cg.osm.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.osm.entity.Product;
import com.cg.osm.entity.SweetOrder;
import com.cg.osm.error.CustomerNotFoundException;
import com.cg.osm.error.ProductNotFoundException;
import com.cg.osm.error.SweetOrderNotFoundException;
import com.cg.osm.repository.CustomerRepository;
import com.cg.osm.repository.ProductRepository;
import com.cg.osm.repository.SweetOrderJpaRepository;

@Service
public class SweetOrderServiceImpl implements SweetOrderService {

	@Autowired
	private SweetOrderJpaRepository sweetOrderRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private ProductRepository productRepository;

	@Override
	public SweetOrder addSweetOrder(SweetOrder sweetOrder) throws CustomerNotFoundException, ProductNotFoundException {
		int customerId = sweetOrder.getCustomer().getUserId();
		if (!customerRepository.existsById(customerId))
			throw new CustomerNotFoundException("No such customer found");
		sweetOrder.setCustomer(customerRepository.getOne(customerId));
		LocalDate date = LocalDate.now();
		sweetOrder.setDate(date);
		List<Product> productList = sweetOrder.getProdList();
		for (Product p : productList) {
			if (productRepository.existsById(p.getProdId()))
				continue;
			else
				throw new ProductNotFoundException("No such product found");
		}
		sweetOrder.setProdList(productList);
		sweetOrder.setTotalCost(calculateTotalCost(productList));
		SweetOrder sweetOrder1 = sweetOrderRepository.saveAndFlush(sweetOrder);
		return sweetOrder1;
	}

	@Override
	public String cancelSweetOrder(int sweetOrderId) throws SweetOrderNotFoundException {
		boolean sweetOrder_found = sweetOrderRepository.existsById(sweetOrderId);
		if (sweetOrder_found) {
			sweetOrderRepository.deleteById(sweetOrderId);
			return "Order deleted";
		}
		throw new SweetOrderNotFoundException("No such order found to delete with id:" + sweetOrderId);
	}

	@Override
	public List<SweetOrder> ShowAllSweetOrder() throws SweetOrderNotFoundException {
		List<SweetOrder> sweetorderList = sweetOrderRepository.findAll();
		if (sweetorderList.size() == 0) {
			throw new SweetOrderNotFoundException("No orders found");
		} else {
			return sweetorderList;
		}
	}

	@Override
	public double calculateTotalCost(List<Product> productList) {
		double sum = 0;
		for (Product product : productList) {
			sum = sum + product.getProdPrice();
		}
		return sum;
	}

	@Override
	public List<SweetOrder> findOrdersByCustomerId(int customerId)
			throws CustomerNotFoundException, SweetOrderNotFoundException {
		boolean customerfound = customerRepository.existsById(customerId);
		if (customerfound) {
			List<SweetOrder> orderList = sweetOrderRepository.findOrdersByCustomerId(customerId);
			if (orderList.size() == 0) {
				throw new SweetOrderNotFoundException("No sweet orders by the given customer");
			} else {
				return orderList;
			}

		} else
			throw new CustomerNotFoundException("No such Customer found with id:" + customerId);
	}

	@Override
	public Optional<SweetOrder> findOrderById(int orderId) throws SweetOrderNotFoundException {
		Optional<SweetOrder> sweetOrder = sweetOrderRepository.findById(orderId);
		if (sweetOrder.isPresent()) {
			return sweetOrder;
		}
		throw new SweetOrderNotFoundException("No such order found with id:" + orderId);

	}

}