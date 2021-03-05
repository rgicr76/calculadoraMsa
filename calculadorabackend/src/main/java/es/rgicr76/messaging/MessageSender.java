package es.rgicr76.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

import javax.annotation.PostConstruct;

@Component
public class MessageSender {

	private static final Logger LOG = LoggerFactory.getLogger(MessageSender.class);

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private MessageConverter jsonMessageConverter;

	@PostConstruct
	private void initRabbit() {
		rabbitTemplate.setMessageConverter(jsonMessageConverter);
	}

	public String sendMessage(String queueName, Object event) {
		try {
			LOG.info("Sending event message to Queue '" + queueName + "' with Json: " + event2PrettyJsonString(event));
		} catch (JsonProcessingException e) {
			LOG.error("Sending event message to Queue '" + queueName + "' with Json noy valid");
		}

		rabbitTemplate.convertAndSend(queueName, event);
		return UUID.randomUUID().toString();
	}

	private String event2PrettyJsonString(Object event) throws JsonProcessingException {
		return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(event);
	}
}
