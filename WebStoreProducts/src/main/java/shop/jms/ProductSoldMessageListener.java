package shop.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import shop.jms.SoldProductDTO;
import shop.products.domain.Stock;
import shop.products.service.ProductCatalogService;

@Component
public class ProductSoldMessageListener {
	@Autowired
	ProductCatalogService productCatalogService;
	
	

	public ProductSoldMessageListener() {
		System.out.println("ProductSoldMessageListener"); 
	}



	@JmsListener(destination="soldProductQueue")
	public void receiveMessage(final SoldProductDTO soldProductDTO) {
		Stock stock = productCatalogService.getStock(soldProductDTO.getProductNumber());
		int newQuantity = stock.getQuantity() - soldProductDTO.getQuantity();
		if (newQuantity <= 0)
			newQuantity = 0;
		productCatalogService.setStock(soldProductDTO.getProductNumber(), newQuantity, stock.getLocationcode());
		System.out
				.println("Quantity of product " + soldProductDTO.getProductNumber() + " is updated to " + newQuantity);
	}
}
