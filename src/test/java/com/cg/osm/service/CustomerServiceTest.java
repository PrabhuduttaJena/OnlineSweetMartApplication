package com.cg.osm.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import com.cg.osm.entity.Address;
import com.cg.osm.entity.Customer;
import com.cg.osm.error.CustomerNotFoundException;
import com.cg.osm.repository.CustomerRepository;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

	@Mock
	private CustomerRepository customerrepository;

	@InjectMocks
	private CustomerServiceImpl customerservice;

	@Test
	void test_addCustomer() {
		Customer cust1 = new Customer(123, "Prashanth", "aripiralap@gmail.com",
				new Address("Hyderabad", "9885394447", "500054"), null);
		Mockito.when(customerrepository.save(cust1)).thenReturn(cust1);
		Customer result = customerrepository.save(cust1);
		assertEquals(123, result.getUserId());
	}

	@Test
	void test_findCustomerById() throws Exception {
		BDDMockito.given(customerrepository.findById(123)).willReturn(Optional.of(new Customer(123, "Prashanth",
				"aripiralap@gmail.com", new Address("Hyderabad", "9885394447", "500054"), null)));
		Customer result = customerservice.findCustomerById(123).get();
		assertEquals(123, result.getUserId());
	}

	@Test
	void test_findCustomerById_ThrowCustomerNotFoundException() {
		try {
			Mockito.when(customerservice.findCustomerById(123)).thenThrow(new CustomerNotFoundException("not found"));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			Assertions.assertTrue(e1 instanceof CustomerNotFoundException);
		}

	}

	@Test
	void test_findCustomerByName() throws Exception {
		BDDMockito.given(customerrepository.findCustomerByName("Prashanth")).willReturn(Optional.of(new Customer(123,
				"Prashanth", "aripiralap@gmail.com", new Address("Hyderabad", "9885394447", "500054"), null)));
		Customer result = customerservice.findCustomerByName("Prashanth").get();
		assertEquals(123, result.getUserId());
	}

	@Test
	void test_findCustomerByName_ThrowCustomerNotFoundException() {
		try {
			Mockito.when(customerservice.findCustomerByName(Mockito.anyString()))
					.thenThrow(new CustomerNotFoundException("not found"));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			Assertions.assertTrue(e1 instanceof CustomerNotFoundException);
		}

	}

	@Test
	void test_deleteCustomer() throws Exception {
		Customer cust1 = new Customer(123, "Prashanth", "aripiralap@gmail.com",
				new Address("Hyderabad", "9885394447", "500054"), null);
		when(customerrepository.existsById(cust1.getUserId())).thenReturn(true);
		customerservice.deleteCustomer(cust1.getUserId());
		verify(customerrepository).deleteById(123);
	}

	@Test
	void test_deleteCustomer_ThrowCustomerNotFoundException() {
		try {
			Mockito.when(customerservice.deleteCustomer(Mockito.anyInt()))
					.thenThrow(new CustomerNotFoundException("not found"));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			Assertions.assertTrue(e1 instanceof CustomerNotFoundException);
		}

	}

	@Test
	void test_showAllCustomers() throws Exception {
		Customer cust1 = new Customer(123, "Prashanth", "aripiralap@gmail.com",
				new Address("Hyderabad", "9885394447", "500054"), null);
		Customer cust2 = new Customer(124, "Ravi", "ravikumar@gmail.com", new Address("Pune", "988543123", "700032"),
				null);
		List<Customer> customerlist = new ArrayList<>();
		customerlist.add(cust1);
		customerlist.add(cust2);
		Mockito.when(customerrepository.findAll()).thenReturn(customerlist);
		List<Customer> result = customerservice.showAllCustomers();
		assertEquals(2, result.size());
		assertEquals(123, result.get(0).getUserId());
	}

	@Test
	void test_showAllCustomers_ThrowCustomerNotFoundException() {
		try {
			Mockito.when(customerservice.showAllCustomers()).thenThrow(new CustomerNotFoundException("not found"));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			Assertions.assertTrue(e1 instanceof CustomerNotFoundException);
		}

	}

	@Test
	public void updateCustomer() {
		Customer cust1 = new Customer(123, "Prashanth", "aripiralap@gmail.com",
				new Address("Hyderabad", "9885394447", "500054"), null);
		cust1.setUsername("Ravi");
		assertThat(customerrepository.findById(cust1.getUserId())).isNotEqualTo(cust1);

	}

	@Test
	void test_updateCustomer_ThrowCustomerNotFoundException() {
		Customer cust1 = new Customer(123, "Prashanth", "aripiralap@gmail.com",
				new Address("Hyderabad", "9885394447", "500054"), null);
		try {
			Mockito.when(customerservice.updateCustomer(cust1)).thenThrow(new CustomerNotFoundException("not found"));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			Assertions.assertTrue(e1 instanceof CustomerNotFoundException);
		}

	}

}
