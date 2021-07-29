package com.cg.springjpahibernatedemo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "products")
@ApiModel(description = "All details about the Product. ")
public class Products {

    @ApiModelProperty(notes = "The database generated Product ID")
    @Id
    private long id;

    @ApiModelProperty(notes = "The  Product name")
    @NotEmpty(message = "Please provide a PRODUCT NAME")
    private String productName;

    @NotNull(message = "Please provide a price")
    @ApiModelProperty(notes = "The Product Price")
    private String productPrice;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(String productPrice) {
		this.productPrice = productPrice;
	}

	public Products(long id, String productName, String productPrice) {
		super();
		this.id = id;
		this.productName = productName;
		this.productPrice = productPrice;
	}

	public Products() {
		super();
	}

	@Override
	public String toString() {
		return "Products [id=" + id + ", productName=" + productName + ", productPrice=" + productPrice + "]";
	}
    
    

    
}