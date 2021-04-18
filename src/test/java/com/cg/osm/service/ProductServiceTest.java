package com.cg.osm.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
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

import com.cg.osm.entity.Category;
import com.cg.osm.entity.Product;
import com.cg.osm.error.CategoryNotFoundException;
import com.cg.osm.error.ProductNotFoundException;
import com.cg.osm.repository.CategoryRepository;
import com.cg.osm.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

	@InjectMocks
	private ProductServiceImpl service;

	@Mock
	private ProductRepository repository;

	@Mock
	private CategoryRepository crepo;

	@Test
	public void addProductTest() throws Exception {
		Category c = new Category(1, "type1");
		LocalDate l = LocalDate.of(2021, 12, 15);
		Product p = new Product(1, "prod1", 200.0, l, c);
		Mockito.when(repository.save(p)).thenReturn(p);
		Product result = repository.save(p);

		Assertions.assertEquals(1, result.getProdId());
	}

	@Test
	public void showAllProductsTest() throws ProductNotFoundException {
		Category c = new Category(1, "type1");
		LocalDate l = LocalDate.of(2021, 11, 15);
		Product p = new Product(2, "prod2", 100.0, l, c);
		Mockito.when(repository.findAll()).thenReturn(Stream.of(p).collect(Collectors.toList()));

		Assertions.assertEquals(1, service.showAllProducts().size());
	}

	@Test
	void test_showAllProductsTest_ThrowProductNotFoundException() {
		try {
			Mockito.when(service.showAllProducts()).thenThrow(new ProductNotFoundException(" Products not found"));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			Assertions.assertTrue(e1 instanceof ProductNotFoundException);
		}

	}

	@Test
	public void findByProductIdTest() throws ProductNotFoundException {
		Category c = new Category(1, "type1");
		LocalDate l = LocalDate.of(2021, 12, 15);
		Product p = new Product(1, "prod3", 200.0, l, c);

		BDDMockito.given(repository.findById(1)).willReturn(Optional.of(p));

		Product result = service.findByProductId(1).get();
		assertEquals(1, result.getProdId());
	}

	@Test
	void test_findByProductIdTest_ThrowProductNotFoundException() {
		try {
			Mockito.when(service.findByProductId(1)).thenThrow(new ProductNotFoundException("Product not found"));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			Assertions.assertTrue(e1 instanceof ProductNotFoundException);
		}

	}

	@Test
	public void findByCategoryIdTest() throws CategoryNotFoundException, ProductNotFoundException {
		Category c = new Category(1, "type1");
		LocalDate l = LocalDate.of(2021, 11, 15);
		Product p = new Product(4, "prod4", 100.0, l, c);
		when(crepo.existsById(1)).thenReturn(true);
		when(repository.findByCategoryId(1)).thenReturn(Stream.of(p).collect(Collectors.toList()));
		List<Product> plist = service.findByCategoryId(1);
		assertEquals(1, plist.size());

	}

	@Test
	void test_findByCategoryIdTest_ThrowProductNotFoundException_CategoryNotFoundException() {
		try {
			Mockito.when(service.findByCategoryId(1)).thenThrow(new CategoryNotFoundException("Category not found"));
		} catch (Exception e1) {
			Assertions.assertFalse(e1 instanceof ProductNotFoundException);
		}

	}

	@Test
	public void deleteProductTest() throws ProductNotFoundException {
		Category c = new Category(1, "type1");
		LocalDate l = LocalDate.of(2021, 12, 15);
		Product p = new Product(1, "prod2", 200.0, l, c);
		when(repository.existsById(p.getProdId())).thenReturn(true);

		service.deleteProduct(p.getProdId());
		verify(repository).deleteByid(1);
	}

	@Test
	void test_deleteProductTest_ThrowProductNotFoundException() {
		try {
			Mockito.when(service.deleteProduct(Mockito.anyInt())).thenThrow(new ProductNotFoundException("not found"));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			Assertions.assertTrue(e1 instanceof ProductNotFoundException);
		}

	}

	@Test
	public void updateProductTest() throws ProductNotFoundException {
		Category c = new Category(1, "type1");
		LocalDate l = LocalDate.of(2021, 12, 15);
		Product p = new Product(5, "prod5", 2000.0, l, c);
		p.setProdName("prod8");

		assertThat(repository.findById(p.getProdId())).isNotEqualTo(p);
	}

	@Test
	void test_updateProductTest_ThrowProductNotFoundException() {
		Category c = new Category(1, "type1");
		LocalDate l = LocalDate.of(2021, 12, 15);
		Product p = new Product(5, "prod5", 2000.0, l, c);
		try {
			Mockito.when(service.updateProduct(p)).thenThrow(new ProductNotFoundException("Product not found"));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			Assertions.assertTrue(e1 instanceof ProductNotFoundException);
		}

	}
}