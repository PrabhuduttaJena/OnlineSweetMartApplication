package com.cg.osm.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.cg.osm.entity.Address;
import com.cg.osm.entity.Category;
import com.cg.osm.entity.Customer;
import com.cg.osm.entity.Product;
import com.cg.osm.entity.SweetOrder;
import com.cg.osm.error.CustomerNotFoundException;
import com.cg.osm.error.SweetOrderNotFoundException;
import com.cg.osm.repository.CustomerRepository;
import com.cg.osm.repository.SweetOrderJpaRepository;

@ExtendWith(MockitoExtension.class)
public class SweetOrderServiceTest {

	@InjectMocks
	private SweetOrderServiceImpl service;
	
	@Mock
	private SweetOrderJpaRepository repository;

	@Mock
	private CustomerRepository customerrepository;

	@Test
	public void test_ShowAllSweetOrder() throws Exception {
		Product p = new Product(1, "sweet1", 200.0, LocalDate.now(), new Category(1, "Ksweet"));
		Product p2 = new Product(2, "sweet2", 200.0, LocalDate.now(), new Category(1, "Ksweet"));
		List<Product> plist = new ArrayList<Product>();
		plist.add(p2);
		plist.add(p);
		SweetOrder s = new SweetOrder(1, plist);
		Mockito.when(repository.findAll())
				.thenReturn(Stream.of(new SweetOrder(1, plist), new SweetOrder(2, plist)).collect(Collectors.toList()));
		assertEquals(2, service.ShowAllSweetOrder().size());
	}

	@Test
	public void test_addSweetorder() throws Exception {
		Product p = new Product(1, "sweet1", 200.0, LocalDate.now(), new Category(1, "KSwwet"));
		List<Product> plist = new ArrayList<Product>();
		plist.add(p);
		SweetOrder s1 = new SweetOrder(1, plist);
		Mockito.when(repository.save(s1)).thenReturn(s1);
		SweetOrder result = repository.save(s1);
		assertEquals(1, result.getOrderId());
	}

	@Test
	public void test_findSweetorderByOrderId() throws Exception {
		BDDMockito.given(repository.findById(10)).willReturn(Optional.of(new SweetOrder(10, null)));
		SweetOrder result = service.findOrderById(10).get();
		assertEquals(10, result.getOrderId());
	}

	@Test
	public void test_deleteOrder() throws Exception {
		Product p = new Product(1, "sweet1", 200.0, LocalDate.now(), new Category(1, "Ksweet"));
		Product p2 = new Product(2, "sweet2", 200.0, LocalDate.now(), new Category(1, "Ksweet"));
		List<Product> plist = new ArrayList<Product>();
		plist.add(p2);
		plist.add(p);
		SweetOrder s = new SweetOrder(1, plist);
		when(repository.existsById(s.getOrderId())).thenReturn(true);
		service.cancelSweetOrder(s.getOrderId());
		verify(repository).deleteById(1);
	}

	@Test
	public void test_findSweetOrderByCustomerId() throws Exception {
		Customer cust1 = new Customer(123, "Prashanth", "aripiralap@gmail.com",
				new Address("Hyderabad", "9885394447", "500054"), null);
		SweetOrder s = new SweetOrder();
		s.setCustomer(cust1);
		SweetOrder s2 = new SweetOrder(2, null);
		s2.setCustomer(cust1);
		List<SweetOrder> orders = new ArrayList<SweetOrder>();
		orders.add(s);
		orders.add(s2);
		BDDMockito.given(customerrepository.existsById(123)).willReturn(true);
		BDDMockito.given(repository.findOrdersByCustomerId(123)).willReturn(orders);
		
		List<SweetOrder> result = service.findOrdersByCustomerId(123);
		assertEquals(2, result.size());
	}

	@Test
	public void test_ShowAllSweetOrder_ThrowSweetOrderNotFoundException() {
		try {
			Mockito.when(service.ShowAllSweetOrder())
					.thenThrow(new SweetOrderNotFoundException("No SweetOrder is found"));
		} catch (Exception e) {
			Assertions.assertTrue(e instanceof SweetOrderNotFoundException);
		}

	}

	@Test
	public void test_findSweetorderByOrderId_ThrowSweetOrderNotFoundException() {
		try {
			Mockito.when(service.findOrderById(Mockito.anyInt()))
					.thenThrow(new SweetOrderNotFoundException("No SweetOrder is found"));
		} catch (Exception e) {
			Assertions.assertTrue(e instanceof SweetOrderNotFoundException);
		}
	}

	@Test
	public void test_findSweetOrderByCustomerId_ThrowCustomerNotFoundException() {
		try {
			Mockito.when(service.findOrdersByCustomerId(Mockito.anyInt()))
					.thenThrow(new CustomerNotFoundException("Customer is not found"));
		} catch (Exception e) {
			Assertions.assertTrue(e instanceof CustomerNotFoundException);
		}

	}

	@Test
	public void test_deleteOrder_ThrowSweetOrderNotFoundException() {
		try {
			Mockito.when(service.cancelSweetOrder(Mockito.anyInt()))
					.thenThrow(new SweetOrderNotFoundException("No orderId is found"));
		} catch (Exception e) {
			Assertions.assertTrue(e instanceof SweetOrderNotFoundException);
		}
	}

}