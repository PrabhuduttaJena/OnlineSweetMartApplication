package com.cg.osm.service;

import java.util.List;
import java.util.Optional;

import com.cg.osm.entity.Address;
import com.cg.osm.entity.Customer;
import com.cg.osm.error.CustomerNotFoundException;

public interface CustomerService {

	public Customer addCustomer(Customer customer);

	public Customer updateCustomer(Customer Customer) throws CustomerNotFoundException;

	public String deleteCustomer(int CustomerId) throws CustomerNotFoundException;

	public List<Customer> showAllCustomers() throws CustomerNotFoundException;

	public Optional<Customer> findCustomerById(int CustomerId) throws CustomerNotFoundException;

	public Customer updateAddress(Address address, int customerId) throws CustomerNotFoundException;

	public Optional<Customer> findCustomerByName(String name) throws CustomerNotFoundException;

}
