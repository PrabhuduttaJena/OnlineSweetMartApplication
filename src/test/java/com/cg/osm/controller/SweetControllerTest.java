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
import com.cg.osm.entity.SweetOrder;
import com.cg.osm.error.CustomerNotFoundException;
import com.cg.osm.error.SweetOrderNotFoundException;
import com.cg.osm.service.SweetOrderServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = SweetController.class)
public class SweetControllerTest {

	@Autowired
	MockMvc mockmvc;

	@MockBean
	SweetOrderServiceImpl sweetService;

	@Autowired
	ObjectMapper mapper;

	@Test
	void test_addSweetOrder() throws Exception {
		SweetOrder so1 = new SweetOrder(123, null);
		BDDMockito.given(sweetService.addSweetOrder(so1)).willReturn(so1);
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/sweetorder")
				.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8").content(this.mapper.writeValueAsBytes(so1));

		mockmvc.perform(builder).andExpect(status().isOk());
	}

	@Test
	void test_findOrderById() throws Exception {
		BDDMockito.given(sweetService.findOrderById(123)).willReturn(Optional.of(new SweetOrder(123, null)));
		mockmvc.perform(MockMvcRequestBuilders.get("/sweetorder/123")).andExpect(status().isOk());
	}

	@Test
	void test_cancelSweetOrder() throws Exception {
		BDDMockito.given(sweetService.cancelSweetOrder(Mockito.anyInt())).willReturn("Order deleted");
		mockmvc.perform(MockMvcRequestBuilders.delete("/sweetorder/123")).andExpect(status().isOk());
	}

	@Test
	void test_retreiveAllSweet() throws Exception {
		SweetOrder s1 = new SweetOrder(1, null);
		SweetOrder s2 = new SweetOrder(2, null);
		List<SweetOrder> orders = new ArrayList<SweetOrder>();
		orders.add(s1);
		orders.add(s2);

		BDDMockito.given(sweetService.ShowAllSweetOrder()).willReturn(orders);
		mockmvc.perform(MockMvcRequestBuilders.get("/sweetorder")).andExpect(status().isOk());
	}

	@Test
	void test_findOrdersByCustomerId() throws Exception {
		SweetOrder s1 = new SweetOrder(1, null);
		SweetOrder s2 = new SweetOrder(2, null);
		List<SweetOrder> orders = new ArrayList<SweetOrder>();
		orders.add(s1);
		orders.add(s2);

		BDDMockito.given(sweetService.findOrdersByCustomerId(123)).willReturn(orders);
		mockmvc.perform(MockMvcRequestBuilders.get("/sweetorder/cust/123")).andExpect(status().isOk());
	}

	@Test
	void test_cancelSweetOrder_ThrowSweetOrderNotFoundException() throws Exception {
		BDDMockito.given(sweetService.cancelSweetOrder(Mockito.anyInt()))
				.willThrow(new SweetOrderNotFoundException(""));
		mockmvc.perform(MockMvcRequestBuilders.delete("/sweetorder/123")).andExpect(status().isNotFound());
	}

	@Test
	void test_retreiveAllSweet_ThrowSweetOrderNotFoundException() throws Exception {
		BDDMockito.given(sweetService.ShowAllSweetOrder()).willThrow(new SweetOrderNotFoundException(""));
		mockmvc.perform(MockMvcRequestBuilders.get("/sweetorder")).andExpect(status().isNotFound());
	}

	@Test
	void test_findOrdersByCustomerId_ThrowCustomerNotFoundException() throws Exception {
		BDDMockito.given(sweetService.findOrdersByCustomerId(Mockito.anyInt()))
				.willThrow(new CustomerNotFoundException(""));
		mockmvc.perform(MockMvcRequestBuilders.get("/sweetorder/cust/101")).andExpect(status().isNotFound());
	}

	@Test
	void test_findOrderById_ThrowSweetOrderNotFoundException() throws Exception {
		BDDMockito.given(sweetService.findOrderById(Mockito.anyInt())).willThrow(new SweetOrderNotFoundException(""));
		mockmvc.perform(MockMvcRequestBuilders.get("/sweetorder/101")).andExpect(status().isNotFound());
	}

}