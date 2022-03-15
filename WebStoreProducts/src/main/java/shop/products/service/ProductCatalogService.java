package shop.products.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shop.dto.ProductAdapter;
import shop.dto.ProductDTO;
import shop.products.domain.Product;
import shop.products.domain.Stock;
import shop.products.repository.ProductRepository;

@Service
public class ProductCatalogService {
	@Autowired
	ProductRepository productRepository;

	public void addProduct(String productnumber, String description, double price) {
		Product product = new Product(productnumber, description,  price);
		productRepository.save(product);
		
	}
	public ProductDTO getProduct(String productnumber) {
		Optional<Product> result = productRepository.findById(productnumber);
		if (result.isPresent())
		  return ProductAdapter.getProductDTO(result.get());
		else
			return null;
	}
	public void setStock(String productnumber, int quantity, String locationcode) {
		Optional<Product> result = productRepository.findById(productnumber);
		if (result.isPresent()) {
			Product product = result.get();
			Stock stock = new Stock(quantity, locationcode);
			product.setStock(stock);
			productRepository.save(product);
		}		
	}
		
	public Stock getStock(String productnumber) {
		Optional<Product> result = productRepository.findById(productnumber);
		if (result.isPresent()) {
			Product product = result.get();
			Stock stock = product.getStock();
			return stock;
		}
		return null;
	}
}
