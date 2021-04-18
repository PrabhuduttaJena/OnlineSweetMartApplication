package com.cg.osm.service;

import java.util.List;
import java.util.Optional;

import com.cg.osm.entity.Product;
import com.cg.osm.error.CategoryNotFoundException;
import com.cg.osm.error.ProductNotFoundException;

public interface ProductService {

	public Product addProduct(Product product) throws CategoryNotFoundException;

	public Product updateProduct(Product product) throws ProductNotFoundException, CategoryNotFoundException;

	public String deleteProduct(int ProductId) throws ProductNotFoundException;

	public List<Product> showAllProducts() throws ProductNotFoundException;

	public Optional<Product> findByProductId(int ProductId) throws ProductNotFoundException;

	public List<Product> findByCategoryId(int categoryId) throws CategoryNotFoundException, ProductNotFoundException;

}