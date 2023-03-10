package com.bookshop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Product {
	@Id
	@Column(name = "productId", insertable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long productId;

	@Column(name = "productName")
  private String productName;

	@Column(name = "description")
	private String description;

	@Column(name = "author")
	private String author;

	@Column(name = "price")
	private float price;

	@Column(name = "imagePath")
	private String imagePath;

}
