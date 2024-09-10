package com.karthik.axonsaga.controller;

import java.util.List;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.karthik.axonsaga.entity.Product;
import com.karthik.productservice.queries.GetAllProductsQuery;
import com.karthik.productservice.queries.GetProductByIdQuery;
import com.karthik.productservice.queries.GetProductByNameQuery;

@RestController
@RequestMapping("/productservice")
public class ProductController {

	@Autowired
	private transient QueryGateway queryGateway;

	@GetMapping("/getAllProducts")
	public List<Product> getAllProductsQuery() {

		GetAllProductsQuery getAllProductsQuery = new GetAllProductsQuery();
		return queryGateway.query(getAllProductsQuery, ResponseTypes.multipleInstancesOf(Product.class)).join();
	}

	@GetMapping("/getProductById/{productId}")
	public Product getProductByIdQuery(@PathVariable String productId) {

		GetProductByIdQuery getProductByIdQuery = new GetProductByIdQuery(productId);
		return queryGateway.query(getProductByIdQuery, ResponseTypes.instanceOf(Product.class)).join();
	}

	@GetMapping("/getProductByName/{productName}")
	public Product getProductByNameQuery(@PathVariable String productName) {

		GetProductByNameQuery getProductByNameQuery = new GetProductByNameQuery(productName);
		return queryGateway.query(getProductByNameQuery, ResponseTypes.instanceOf(Product.class)).join();
	}
}
