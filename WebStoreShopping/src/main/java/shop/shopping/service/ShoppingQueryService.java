package shop.shopping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import shop.shopping.domain.Product;
import shop.shopping.domain.ShoppingCart;
import shop.shopping.domain.ShoppingCartCheckedOutEvent;
import shop.shopping.dto.ProductDTO;
import shop.shopping.dto.ShoppingCartAdapter;
import shop.shopping.dto.ShoppingCartDTO;
import shop.shopping.integration.OrderProxy;
import shop.shopping.integration.ProductCatalogProxy;
import shop.shopping.repository.ShoppingCartRepository;

import java.util.Optional;

@Service
public class ShoppingQueryService {
	@Autowired
	ProductCatalogProxy productCatalogProxy;
	@Autowired
	ShoppingCartRepository shoppingCartRepository;
	@Autowired
	OrderProxy orderProxy;
	@Autowired
	private ApplicationEventPublisher publisher;

	public ShoppingCartDTO getCart(String cartId) {
		Optional<ShoppingCart> cart = shoppingCartRepository.findById(cartId);
		if (cart.isPresent())
		  return ShoppingCartAdapter.getShoppingCartDTO(cart.get());
		else
			return null;
	}
}
