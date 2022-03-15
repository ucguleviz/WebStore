package shop.shopping.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import shop.shopping.dto.ShoppingCartDTO;
import shop.shopping.service.ShoppingCommandService;
import shop.shopping.service.ShoppingQueryService;

@RestController
public class ShoppingController {
	@Autowired
	ShoppingCommandService shoppingCommandService;
	@Autowired
	ShoppingQueryService shoppingQueryService;
	
	@PostMapping(value = "/cart/{cartId}/{productnumber}/{quantity}")
	public ResponseEntity<?> addToCart(@PathVariable String cartId, @PathVariable String productnumber, @PathVariable int quantity) {
		shoppingCommandService.addToCart(cartId, productnumber, quantity);
		return new ResponseEntity<ShoppingCartDTO>(HttpStatus.OK);		
	}
	
	@GetMapping("/cart/{cartId}")
	public ResponseEntity<?> getCart(@PathVariable String cartId) {
		ShoppingCartDTO cart = shoppingQueryService.getCart(cartId);
		return new ResponseEntity<ShoppingCartDTO>(cart, HttpStatus.OK);
	}
	
	@PostMapping(value = "/cart/checkout/{cartId}")
	public ResponseEntity<?> checkoutCart(@PathVariable String cartId) {
		shoppingCommandService.checkout(cartId);
		return new ResponseEntity<ShoppingCartDTO>(HttpStatus.OK);		
	}
	
}
