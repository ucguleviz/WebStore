package shop.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import shop.jms.SoldProductDTO;

@Component
public class JmsSender {
	@Autowired
	JmsTemplate jmsTemplate;

	public void sendJMSMessage(SoldProductDTO soldProductDTO) {
		System.out.println("Sending a JMS message.");
		jmsTemplate.convertAndSend("soldProductQueue",soldProductDTO);
	}
}
