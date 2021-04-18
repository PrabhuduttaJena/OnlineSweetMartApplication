package com.cg.osm.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cg.osm.entity.SweetOrder;
@Repository
public interface SweetOrderJpaRepository extends JpaRepository<SweetOrder,Integer> {

	@Query("SELECT s FROM SweetOrder s WHERE s.customer.userId = ?1")
	public List<SweetOrder> findOrdersByCustomerId(@Param("customerId")int customerId);
	
}