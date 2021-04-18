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

import com.cg.osm.entity.Category;
import com.cg.osm.error.CategoryNotFoundException;
import com.cg.osm.repository.CategoryRepository;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

	@InjectMocks
	private CategoryServiceImpl cservice;
	@Mock
	private CategoryRepository crepository;

	@Test
	public void addCategoryTest() {
		Category c = new Category(1, "type1");

		when(crepository.save(c)).thenReturn(c);

		assertEquals(c, cservice.addCategory(c));
	}

	@Test
	void showAllCategoriesTest() throws Exception {
		Category c = new Category(1, "type1");
		Category c1 = new Category(1, "type1");
		List<Category> categorylist = new ArrayList<>();
		categorylist.add(c);
		categorylist.add(c1);
		Mockito.when(crepository.findAll()).thenReturn(categorylist);
		List<Category> result = cservice.showAllCategories();
		assertEquals(2, result.size());
		assertEquals(1, result.get(0).getCategoryId());
	}

	@Test
	void showAllCategoriesTest_ThrowCategoryNotFoundException() {
		try {
			Mockito.when(cservice.showAllCategories()).thenThrow(new CategoryNotFoundException("not found"));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			Assertions.assertTrue(e1 instanceof CategoryNotFoundException);
		}

	}

	@Test
	void deleteCategoryTest() throws Exception {
		Category c = new Category(1, "type1");

		when(crepository.existsById(c.getCategoryId())).thenReturn(true);
		cservice.deleteCategoryById(c.getCategoryId());
		verify(crepository).deleteById(1);
	}

	@Test
	void deleteCategoryTest_ThrowCategoryNotFoundException() {
		try {
			Mockito.when(cservice.deleteCategoryById(Mockito.anyInt()))
					.thenThrow(new CategoryNotFoundException("not found"));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			Assertions.assertTrue(e1 instanceof CategoryNotFoundException);
		}

	}

	@Test
	public void updateCategoryTest() {
		Category c = new Category(1, "type1");

		c.setName("sweettype1");
		assertThat(crepository.findById(c.getCategoryId())).isNotEqualTo(c);

	}

	@Test
	void updateCustomerTest_ThrowCustomerNotFoundException() {
		Category c = new Category(1, "type1");
		try {
			Mockito.when(cservice.updateCategory(c)).thenThrow(new CategoryNotFoundException("not found"));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			Assertions.assertTrue(e1 instanceof CategoryNotFoundException);
		}

	}

	@Test
	void findByCategoryIdTest() throws Exception {
		BDDMockito.given(crepository.findById(1)).willReturn(Optional.of(new Category(1, "sweetitem1")));
		Category result = cservice.findByCategoryId(1).get();
		assertEquals(1, result.getCategoryId());

	}

	@Test
	void findCategoryByIdTest_ThrowCategoryNotFoundException() {
		try {
			Mockito.when(cservice.findByCategoryId(1)).thenThrow(new CategoryNotFoundException("not found"));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			Assertions.assertTrue(e1 instanceof CategoryNotFoundException);
		}

	}
}
