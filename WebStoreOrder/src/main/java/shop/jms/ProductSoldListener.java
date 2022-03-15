package shop.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import shop.order.domain.ProductSoldEvent;


@Component
public class ProductSoldListener {
	@Autowired
	private JmsSender JmsSender;

	@EventListener
	public void onProductSold(ProductSoldEvent productSoldEvent) {
		SoldProductDTO soldProductDTO = new SoldProductDTO(productSoldEvent.getProductNumber(),productSoldEvent.getQuantity());
		JmsSender.sendJMSMessage(soldProductDTO);
	}
}
