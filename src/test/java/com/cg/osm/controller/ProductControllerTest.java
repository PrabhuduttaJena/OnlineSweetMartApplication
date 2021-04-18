package com.cg.osm.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
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
import com.cg.osm.entity.Category;
import com.cg.osm.entity.Product;
import com.cg.osm.error.CategoryNotFoundException;
import com.cg.osm.error.ProductNotFoundException;
import com.cg.osm.service.CategoryServiceImpl;
import com.cg.osm.service.ProductServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ProductController.class)
public class ProductControllerTest {

	@Autowired
	MockMvc mockmvc;

	@MockBean
	ProductServiceImpl service;
	@MockBean
	CategoryServiceImpl cservice;

	@Autowired
	ObjectMapper mapper;

	@Test
	void test_addCategory() throws Exception {
		Category c = new Category(1, "type1");
		BDDMockito.given(cservice.addCategory(c)).willReturn(c);
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/category")
				.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8").content(this.mapper.writeValueAsBytes(c));

		mockmvc.perform(builder).andExpect(status().isOk());
	}

	@Test
	void test_deleteCategory() throws Exception {
		BDDMockito.given(cservice.deleteCategoryById(Mockito.anyInt())).willReturn("Category Deleted Successfully");
		mockmvc.perform(MockMvcRequestBuilders.delete("/category/1")).andExpect(status().isOk());
	}

	@Test
	void test_deleteCategory_ThrowCustomerNotFoundException() throws Exception {
		BDDMockito.given(cservice.deleteCategoryById(Mockito.anyInt()))
				.willThrow(new CategoryNotFoundException("category not found"));
		mockmvc.perform(MockMvcRequestBuilders.delete("/customer/101")).andExpect(status().isNotFound());
	}

	@Test
	void test_showAllCategories() throws Exception {
		Category c = new Category(1, "type1");
		Category c1 = new Category(2, "type2");
		List<Category> categorylist = new ArrayList<>();
		categorylist.add(c);
		categorylist.add(c1);
		BDDMockito.given(cservice.showAllCategories()).willReturn(categorylist);
		mockmvc.perform(MockMvcRequestBuilders.get("/category")).andExpect(status().isOk());
	}

	@Test
	void test_showAllCategories_ThrowCategoryNotFountException() throws Exception {
		BDDMockito.given(cservice.showAllCategories()).willThrow(new CategoryNotFoundException("No category"));
		mockmvc.perform(MockMvcRequestBuilders.get("/category")).andExpect(status().isNotFound());
	}

	@Test
	void test_updateCategory() throws Exception {
		Category c = new Category(1, "type1");

		BDDMockito.given(cservice.updateCategory(c)).willReturn(c);
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/category")
				.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8").content(this.mapper.writeValueAsBytes(c));

		mockmvc.perform(builder).andExpect(status().isOk());
	}

	@Test
	void test_updateCategory_ThrowCategoryNotFoundException() throws Exception {
		Category c = new Category(1, "type1");
		BDDMockito.given(cservice.updateCategory(Mockito.any(Category.class)))
				.willThrow(new CategoryNotFoundException("category not found"));
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/category")
				.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8").content(this.mapper.writeValueAsBytes(c));

		mockmvc.perform(builder).andExpect(status().isNotFound());
	}

	@Test
	void findProductByIdTest() throws Exception {
		Category c = new Category(1, "type1");

		BDDMockito.given(cservice.findByCategoryId(1)).willReturn(Optional.of(c));
		mockmvc.perform(MockMvcRequestBuilders.get("/category/1")).andExpect(status().isOk());
	}

	@Test
	void findCategoryByIdTest_ThrowCategoryNotFoundException() throws Exception {
		BDDMockito.given(cservice.findByCategoryId(Mockito.anyInt()))
				.willThrow(new CategoryNotFoundException("category not found"));
		mockmvc.perform(MockMvcRequestBuilders.get("/category/1")).andExpect(status().isNotFound());
	}

	@Test
	void test_showAllProducts() throws Exception {
		Category c = new Category(1, "type1");
		LocalDate l = LocalDate.of(2021, 12, 15);
		Product p1 = new Product(1, "prod1", 200.0, l, c);
		Product p2 = new Product(2, "prod2", 300.0, l, c);
		List<Product> plist = new ArrayList<>();
		plist.add(p1);
		plist.add(p2);
		Mockito.when(service.showAllProducts()).thenReturn(plist);
		mockmvc.perform(MockMvcRequestBuilders.get("/product").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	void test_showAllProducts_ThrowProductNotFoundException() throws Exception {
		BDDMockito.given(service.showAllProducts()).willThrow(new ProductNotFoundException("No products available"));
		mockmvc.perform(MockMvcRequestBuilders.get("/product")).andExpect(status().isNotFound());
	}

	@Test
	void test_addProduct() throws Exception {
		Category c = new Category(1, "type1");
		LocalDate l = LocalDate.of(2021, 12, 15);
		Product p1 = new Product(1, "prod1", 200.0, l, c);
		BDDMockito.given(service.addProduct(p1)).willReturn(p1);
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/product")
				.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8").content(this.mapper.writeValueAsBytes(p1));

		mockmvc.perform(builder).andExpect(status().isOk());
	}

	@Test
	void test_addProduct_ThrowCategoryNotFoundException() throws Exception {
		Category c = new Category(1, "type1");
		LocalDate l = LocalDate.of(2021, 12, 15);
		Product p1 = new Product(1, "prod1", 200.0, l, c);
		BDDMockito.given(service.addProduct(Mockito.any(Product.class)))
				.willThrow(new CategoryNotFoundException("Category not found"));
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/product")
				.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8").content(this.mapper.writeValueAsBytes(p1));
		mockmvc.perform(builder).andExpect(status().isNotFound());
	}

	@Test
	void test_updateProduct() throws Exception {
		Category c = new Category(1, "type1");
		LocalDate l = LocalDate.of(2021, 12, 15);
		Product p1 = new Product(1, "prod1", 200.0, l, c);
		BDDMockito.given(service.updateProduct(p1)).willReturn(p1);
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/product")
				.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8").content(this.mapper.writeValueAsBytes(p1));

		mockmvc.perform(builder).andExpect(status().isOk());
	}

	@Test
	void test_updateProduct_ThrowProductNotFoundException() throws Exception {
		Category c = new Category(1, "type1");
		LocalDate l = LocalDate.of(2021, 12, 15);
		Product p1 = new Product(1, "prod1", 200.0, l, c);
		BDDMockito.given(service.updateProduct(Mockito.any(Product.class)))
				.willThrow(new ProductNotFoundException("Product not found"));
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/product")
				.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8").content(this.mapper.writeValueAsBytes(p1));

		mockmvc.perform(builder).andExpect(status().isNotFound());
	}

	@Test
	void test_deleteProduct() throws Exception {
		BDDMockito.given(service.deleteProduct(Mockito.anyInt())).willReturn("Product Deleted Successfully");
		mockmvc.perform(MockMvcRequestBuilders.delete("/product/1")).andExpect(status().isOk());
	}

	@Test
	void test_deleteProduct_ProductNotFoundException() throws Exception {
		BDDMockito.given(service.deleteProduct(Mockito.anyInt()))
				.willThrow(new ProductNotFoundException("Product not found"));
		mockmvc.perform(MockMvcRequestBuilders.delete("/product/1")).andExpect(status().isNotFound());
	}

	@Test
	void test_findProductById() throws Exception {
		Category c = new Category(1, "type1");
		LocalDate l = LocalDate.of(2021, 12, 15);
		Product p1 = new Product(1, "prod1", 200.0, l, c);
		BDDMockito.given(service.findByProductId(1)).willReturn(Optional.of(p1));
		mockmvc.perform(MockMvcRequestBuilders.get("/product/1")).andExpect(status().isOk());
	}

	@Test
	void test_findProductById_ThrowProductNotFoundException() throws Exception {
		BDDMockito.given(service.findByProductId(Mockito.anyInt()))
				.willThrow(new ProductNotFoundException("Product not found"));
		mockmvc.perform(MockMvcRequestBuilders.get("/product/1")).andExpect(status().isNotFound());
	}

	@Test
	void test_findProductByCategory() throws Exception {
		Category c = new Category(1, "type1");
		LocalDate l = LocalDate.of(2021, 12, 15);
		Product p1 = new Product(1, "prod1", 200.0, l, c);
		List<Product> plist = new ArrayList<>();
		plist.add(p1);
		BDDMockito.given(service.findByCategoryId(1)).willReturn(plist);
		mockmvc.perform(MockMvcRequestBuilders.get("/product/category/1")).andExpect(status().isOk());
	}

	@Test
	void test_findProductByCategory_ThrowProductNotFoundException_CategoryNotFoundException() throws Exception {
		BDDMockito.given(service.findByCategoryId(Mockito.anyInt())).willThrow(
				new CategoryNotFoundException("Category not found"), new ProductNotFoundException("No products found"));
		mockmvc.perform(MockMvcRequestBuilders.get("/product/category/1")).andExpect(status().isNotFound());
	}

}
