package com.arun.MyFirstProject.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.arun.MyFirstProject.model.Product;
import com.arun.MyFirstProject.repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	ProductRepository productRepository;
	
	public List<Product> getAllProducts() {
		System.out.println(productRepository.count());
		System.out.println(productRepository.findAll());
		return productRepository.findAll();
	}
	
	public Product getProductById(int prodId) {
		return productRepository.findById(prodId).orElse(new Product());
	}
	public void addProduct(Product product) {
		productRepository.save(product);
	}
	public Product updateProduct(Product product, MultipartFile imageFile, int prodId) throws IOException {
		product.setImageName(imageFile.getOriginalFilename());
		product.setImageType(imageFile.getContentType());
		product.setImageData(imageFile.getBytes());
		return productRepository.save(product);
	}
	public void deleteProduct(int prodId) {
		productRepository.deleteById(prodId);
	}

	public Product addProduct(Product product, MultipartFile imageFile) throws IOException {
		product.setImageName(imageFile.getOriginalFilename());
		product.setImageType(imageFile.getContentType());
		product.setImageData(imageFile.getBytes());
		return productRepository.save(product);
	}

	public Product getImageByProductId(int prodId) {
		return productRepository.findById(prodId).orElse(new Product());
	}

	public List<Product> searchByWord(String keyword) {
		return productRepository.searchProduct(keyword);
	}
}
