package com.cg.osm.service;

import java.util.List;
import java.util.Optional;

import com.cg.osm.entity.Category;
import com.cg.osm.error.CategoryNotFoundException;

public interface CategoryService {

	public Category addCategory(Category category);

	public Category updateCategory(Category category) throws CategoryNotFoundException;

	public String deleteCategoryById(int categoryId) throws CategoryNotFoundException;

	public List<Category> showAllCategories() throws CategoryNotFoundException;

	public Optional<Category> findByCategoryId(int categoryid) throws CategoryNotFoundException;

}