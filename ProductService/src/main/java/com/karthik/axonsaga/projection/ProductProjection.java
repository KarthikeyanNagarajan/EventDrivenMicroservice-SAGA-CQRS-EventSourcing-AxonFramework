package com.karthik.axonsaga.projection;

import java.util.List;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.karthik.axonsaga.entity.Product;
import com.karthik.axonsaga.repository.ProductRepository;
import com.karthik.productservice.queries.GetAllProductsQuery;
import com.karthik.productservice.queries.GetProductByIdQuery;
import com.karthik.productservice.queries.GetProductByNameQuery;

@Component
public class ProductProjection {

	@Autowired
	ProductRepository productRepository;

	@QueryHandler
	public List<Product> getAllProductsQuery(GetAllProductsQuery query) {

		return productRepository.findAll();
	}

	@QueryHandler
	public Product getProductByIdQuery(GetProductByIdQuery query) {

		return productRepository.findById(query.getId()).orElse(null);
	}
	
	@QueryHandler
	public Product getProductByNameQuery(GetProductByNameQuery query) {

		return productRepository.findByName(query.getName()).orElse(null);
	}
}
