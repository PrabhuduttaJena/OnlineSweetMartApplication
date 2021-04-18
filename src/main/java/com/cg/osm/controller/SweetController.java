package com.cg.osm.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.cg.osm.entity.SweetOrder;
import com.cg.osm.error.CustomerNotFoundException;
import com.cg.osm.error.ProductNotFoundException;
import com.cg.osm.error.SweetOrderNotFoundException;
import com.cg.osm.service.SweetOrderServiceImpl;

@RestController
public class SweetController {
	
	/*
	 * Injecting Service layer into controller layer
	 */
    @Autowired
	private SweetOrderServiceImpl sweetService;
   
    /*
     * Creating a Sweet Order
     * throws exception either if the customer is not found or the product is not found
     */
    @PostMapping(path="/sweetorder")
	public SweetOrder addSweetOrder(@RequestBody @Valid SweetOrder sweetOrder) throws CustomerNotFoundException, ProductNotFoundException{/*@PathVariable("customerId") int customerId)*/ 
		return sweetService.addSweetOrder(sweetOrder);
	}
    
    /*
     * Deleting a Sweet Order
     * and throw an exception if there's no such sweet order
     */
    @DeleteMapping(path="/sweetorder/{sweetOrderId}")
	public String cancelSweetOrder(@PathVariable("sweetOrderId") int sweetOrderId) throws SweetOrderNotFoundException{
    	return sweetService.cancelSweetOrder(sweetOrderId);
	}
    
    /*
     * Retrieving all the sweet orders
     * throwing exception if there are no sweet orders
     */
    @GetMapping(path="/sweetorder")
    public List<SweetOrder> retreiveAllSweet() throws SweetOrderNotFoundException
    {
     return sweetService.ShowAllSweetOrder();
    }
 
    /*
     * Getting all the orders placed by customer given his/her ID
     * throws CustomerNotFoundException if the customer is not found
     * or SweetOrderNotFoundException if the customer has not placed any orders yet.
     */
    @GetMapping(path="/sweetorder/cust/{customerId}")
	public List<SweetOrder> findOrdersByCustomerId(@PathVariable("customerId") int customerId) throws CustomerNotFoundException,SweetOrderNotFoundException{
		
    	return sweetService.findOrdersByCustomerId(customerId);
	}
    /*
     * Finding an order by its ID
     * throw exception if there is no such order with given ID
     */
    @GetMapping(path="/sweetorder/{orderId}")
    public SweetOrder findOrderBySweetOrderId(@PathVariable("orderId") int orderId) throws SweetOrderNotFoundException{
    	Optional<SweetOrder> sweetorder=sweetService.findOrderById(orderId);
    	return sweetorder.get();
    }
}