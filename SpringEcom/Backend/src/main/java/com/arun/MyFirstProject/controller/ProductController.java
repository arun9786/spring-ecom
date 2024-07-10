package com.arun.MyFirstProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.arun.MyFirstProject.model.Product;
import com.arun.MyFirstProject.service.ProductService;

import java.util.*;


@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("/products")
	public List<Product> getAllProducts(){
		return productService.getAllProducts();
	}
	
	@GetMapping("/product/{prodId}")
	public Product getProductById(@PathVariable int prodId) {
		return productService.getProductById(prodId);	
	}
	
	@PostMapping("/product")
	public ResponseEntity<?> addProduct(@RequestPart Product product,
			@RequestPart MultipartFile imageFile){
		try{
			Product product2= productService.addProduct(product,imageFile);
			return new ResponseEntity(product2,HttpStatus.CREATED);
		}catch(Exception e) {
			return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/product/{prodId}/image")
	public ResponseEntity<byte[]> getImageByProductId(@PathVariable int prodId){
		Product product = productService.getImageByProductId(prodId);
		byte[] imageFile=product.getImageData();
		return ResponseEntity.ok().contentType(MediaType.valueOf(product.getImageType()))
				.body(imageFile);
	}
	
	@PutMapping("/product/{prodId}")
	public ResponseEntity<?> updateProduct(@PathVariable int prodId, @RequestPart Product product,
			@RequestPart MultipartFile imageFile){
		try{
			Product product2= productService.updateProduct(product,imageFile,prodId);
			return new ResponseEntity(product2,HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/product/{prodId}")
	public void deleteProduct(@PathVariable int prodId){
		productService.deleteProduct(prodId);
	}
	
	@GetMapping("/products/search")
	public ResponseEntity<List<Product>> searchResult(@RequestParam String keyword){
		 List<Product> list= productService.searchByWord(keyword);
		 return new ResponseEntity(list, HttpStatus.OK);
	}
	
}
