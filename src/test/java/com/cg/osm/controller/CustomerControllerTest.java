package com.cg.osm.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.cg.osm.entity.Address;
import com.cg.osm.entity.Customer;
import com.cg.osm.error.CustomerNotFoundException;
import com.cg.osm.service.CustomerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CustomerController.class)
public class CustomerControllerTest {

	@Autowired
	MockMvc mockmvc;

	@MockBean
	CustomerServiceImpl customerservice;

	@Autowired
	ObjectMapper mapper;

	@Test
	void test_findCustomerById() throws Exception {
		BDDMockito.given(customerservice.findCustomerById(123)).willReturn(Optional.of(new Customer(123, "Prashanth",
				"aripiralap@gmail.com", new Address("Hyderabad", "9885394447", "500054"), null)));
		mockmvc.perform(MockMvcRequestBuilders.get("/customer/123")).andExpect(status().isOk());
	}

	@Test
	void test_findCustomerById_ThrowCustomerNotFoundException() throws Exception {
		BDDMockito.given(customerservice.findCustomerById(Mockito.anyInt()))
				.willThrow(new CustomerNotFoundException("Customer not found"));
		mockmvc.perform(MockMvcRequestBuilders.get("/customer/101")).andExpect(status().isNotFound());
	}

	@Test
	void test_addCustomer() throws Exception {
		Customer cust1 = new Customer(123, "Prashanth", "aripiralap@gmail.com",
				new Address("Hyderabad", "9885394447", "500054"), null);
		BDDMockito.given(customerservice.addCustomer(cust1)).willReturn(cust1);
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/customer")
				.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8").content(this.mapper.writeValueAsBytes(cust1));

		mockmvc.perform(builder).andExpect(status().isOk());
	}

	@Test
	void test_deleteCustomer() throws Exception {
		BDDMockito.given(customerservice.deleteCustomer(Mockito.anyInt())).willReturn("Customer Deleted Successfully");
		mockmvc.perform(MockMvcRequestBuilders.delete("/customer/101")).andExpect(status().isOk());
	}

	@Test
	void test_deleteCustomer_ThrowCustomerNotFoundException() throws Exception {
		BDDMockito.given(customerservice.deleteCustomer(Mockito.anyInt()))
				.willThrow(new CustomerNotFoundException("Customer not found"));
		mockmvc.perform(MockMvcRequestBuilders.delete("/customer/101")).andExpect(status().isNotFound());
	}

	@Test
	void test_findCustomerByName() throws Exception {
		BDDMockito.given(customerservice.findCustomerByName("Prashanth")).willReturn(Optional.of(new Customer(123,
				"Prashanth", "aripiralap@gmail.com", new Address("Hyderabad", "9885394447", "500054"), null)));
		mockmvc.perform(MockMvcRequestBuilders.get("/customer/name/Prashanth")).andExpect(status().isOk());
	}

	@Test
	void test_findCustomerByName_ThrowCustomerNotFoundException() throws Exception {
		BDDMockito.given(customerservice.findCustomerByName(Mockito.anyString()))
				.willThrow(new CustomerNotFoundException("Customer not found"));
		mockmvc.perform(MockMvcRequestBuilders.get("/customer/name/Prashanth")).andExpect(status().isNotFound());
	}

	@Test
	void test_showAllCustomers() throws Exception {
		Customer cust1 = new Customer(123, "Prashanth", "aripiralap@gmail.com",
				new Address("Hyderabad", "9885394447", "500054"), null);
		Customer cust2 = new Customer(124, "Ravi", "ravikumar@gmail.com", new Address("Pune", "9885454447", "500054"),
				null);
		List<Customer> customerlist = new ArrayList<>();
		customerlist.add(cust1);
		customerlist.add(cust2);
		BDDMockito.given(customerservice.showAllCustomers()).willReturn(customerlist);
		mockmvc.perform(MockMvcRequestBuilders.get("/customer")).andExpect(status().isOk());
	}

	@Test
	void test_showAllCustomers_ThrowCustomerNotFountException() throws Exception {
		BDDMockito.given(customerservice.showAllCustomers()).willThrow(new CustomerNotFoundException("No customers"));
		mockmvc.perform(MockMvcRequestBuilders.get("/customer")).andExpect(status().isNotFound());
	}

	@Test
	void test_updateCustomer() throws Exception {
		Customer cust1 = new Customer(123, "Prashanth", "aripiralap@gmail.com",
				new Address("Hyderabad", "9885394447", "500054"), null);
		BDDMockito.given(customerservice.updateCustomer(cust1)).willReturn(cust1);
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/customer")
				.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8").content(this.mapper.writeValueAsBytes(cust1));

		mockmvc.perform(builder).andExpect(status().isOk());
	}

	@Test
	void test_updateCustomer_ThrowCustomerNotFoundException() throws Exception {
		Customer cust1 = new Customer(123, "Prashanth", "aripiralap@gmail.com",
				new Address("Hyderabad", "9885394447", "500054"), null);
		BDDMockito.given(customerservice.updateCustomer(Mockito.any(Customer.class)))
				.willThrow(new CustomerNotFoundException("Customer not found"));
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/customer")
				.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8").content(this.mapper.writeValueAsBytes(cust1));

		mockmvc.perform(builder).andExpect(status().isNotFound());
	}

}
