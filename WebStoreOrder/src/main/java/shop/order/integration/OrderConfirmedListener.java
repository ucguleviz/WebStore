package shop.order.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import shop.order.domain.OrderConfirmedEvent;


@Component
public class OrderConfirmedListener {
	@Autowired
	private EmailSender emailSender;
	
	  @EventListener
	  public void onOrderConfirmedEvent(OrderConfirmedEvent orderConfirmedEvent) {
		  emailSender.sendEmail("Order is confirmed, orderNumber="+orderConfirmedEvent.getOrder().getOrdernumber(), orderConfirmedEvent.getOrder().getCustomer().getEmail());
	  }
}


