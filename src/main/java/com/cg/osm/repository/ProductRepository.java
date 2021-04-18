package com.cg.osm.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cg.osm.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product , Integer>
{
	@Transactional 
	@Modifying
	@Query("DELETE FROM Product p WHERE p.prodid=:productId")
	int deleteByid(@Param("productId")int productId);
	
	@Query("select p from Product p where p.category.categoryId=?1")
	public List<Product> findByCategoryId(int categoryid);
	
}