package com.cg.springjpahibernatedemo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.springjpahibernatedemo.entity.Products;
import com.cg.springjpahibernatedemo.exception.ResourceNotFoundException;
import com.cg.springjpahibernatedemo.repository.ProductRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@RestController
@RequestMapping("/api/v1")
@Api(value = "Product Management System", description = "Operations pertaining to Product in Product Management System")
@Validated
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @ApiOperation(value = "View a list of available products", response = List.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully retrieved list"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping("/products")
    public List < Products > getAllProducts() {
        return productRepository.findAll();
    }

    @ApiOperation(value = "Get an products by Id")
    @GetMapping("/products/{id}")
    public ResponseEntity < Products > getProductById(
        @ApiParam(value = "Product id from which products object will retrieve", required = true) @PathVariable(value = "id") @Positive Long productId)
    throws ResourceNotFoundException {
    	Products product = productRepository.findById(productId)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found for this id :: " + productId));
        return ResponseEntity.ok().body(product);
    }

    @ApiOperation(value = "Add an Product")
    @PostMapping("/products")
    public Products createproducts(
        @ApiParam(value = "products object store in database table", required = true)@Valid @RequestBody Products product) {
        return productRepository.save(product);
    }

    @ApiOperation(value = "Update an products")
    @PutMapping("/products/{id}")
    public ResponseEntity < Products > updateproducts(
        @ApiParam(value = "products Id to update products object", required = true) @PathVariable(value = "id") Long productsId,
        @ApiParam(value = "Update products object", required = true)@Valid @RequestBody Products productsDetails) throws ResourceNotFoundException {
    	Products products = productRepository.findById(productsId)
            .orElseThrow(() -> new ResourceNotFoundException("products not found for this id :: " + productsId));

    	products.setProductName(productsDetails.getProductName());
    	products.setProductPrice(productsDetails.getProductPrice());
    	
        final Products updatedProducts = productRepository.save(products);
        return ResponseEntity.ok(updatedProducts);
    }

    @ApiOperation(value = "Delete an Products")
    @DeleteMapping("/products/{id}")
    public Map < String, Boolean > deleteproducts(
        @ApiParam(value = "Product Id from which product object will delete from database table", required = true) @PathVariable(value = "id") Long productId)
    throws ResourceNotFoundException {
    	Products products = productRepository.findById(productId)
            .orElseThrow(() -> new ResourceNotFoundException("products not found for this id :: " + productId));

    	productRepository.delete(products);
        Map < String, Boolean > response = new HashMap < > ();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}