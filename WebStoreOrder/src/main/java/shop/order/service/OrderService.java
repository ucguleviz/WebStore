package shop.order.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import shop.order.domain.Order;
import shop.order.domain.OrderConfirmedEvent;
import shop.order.domain.OrderFactory;
import shop.order.domain.OrderLine;
import shop.order.domain.ProductSoldEvent;
import shop.order.dto.*;
import shop.order.integration.CustomerProxy;
import shop.order.integration.EmailSender;
import shop.order.integration.Logger;
import shop.order.repository.OrderRepository;


@Service
public class OrderService {
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	CustomerProxy customerProxy;
	@Autowired
	EmailSender emailSender;
	@Autowired
	Logger logger;
	@Autowired
	private ApplicationEventPublisher publisher;

	public OrderDTO getOrder(String orderNumber) {
		Optional<Order> optOrder = orderRepository.findById(orderNumber);
		if (optOrder.isPresent()) {
			return OrderAdapter.getOrderDTO(optOrder.get());
		} else
			return null;
	}
	
	public void createOrder(ShoppingCartDTO shoppingCartDTO) {
		Order order = OrderFactory.createOrder(shoppingCartDTO);
		orderRepository.save(order);
	}
	
	public void confirm(String orderNumber) {
		Optional<Order> optOrder = orderRepository.findById(orderNumber);
		if (optOrder.isPresent()) {
			Order order= optOrder.get();
			order.confirm();
			emailSender.sendEmail("Thank you for your order with order number "+order.getOrdernumber(), "customer@gmail.com");
			logger.log("new order with order number "+order.getOrdernumber());
			//publish event
			OrderConfirmedEvent event = new OrderConfirmedEvent(OrderAdapter.getOrderDTO(order));
			publisher.publishEvent(event);
			//publish event for each product
			for (OrderLine orderline : order.getOrderlineList()) {
				ProductSoldEvent productSoldEvent = new ProductSoldEvent(orderline.getProduct().getProductnumber(), orderline.getQuantity());
				publisher.publishEvent(productSoldEvent);
			}
		} 
	}

	public void setCustomer(String orderNumber, String customerNumber) {
		Optional<Order> optOrder = orderRepository.findById(orderNumber);
		if (optOrder.isPresent()) {
			Order order = optOrder.get();
			OrderCustomerDTO customerDTO = customerProxy.getOrderCustomer(customerNumber);
			if(customerDTO!=null) {
				order.setCustomer(OrderCustomerAdapter.getCustomer(customerDTO));
			}
			orderRepository.save(order);
		}		
	}

}
