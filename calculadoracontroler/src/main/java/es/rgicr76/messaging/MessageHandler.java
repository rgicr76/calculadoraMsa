package es.rgicr76.messaging;

import static es.rgicr76.messaging.Queues.QUEUE_CALCULADORA_SERVICE;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.rgicr76.service.CalculadoraControllerService;

@Component
public class MessageHandler {

    private static final Logger LOG = LoggerFactory.getLogger(MessageHandler.class);

	@Autowired
	private CalculadoraControllerService calculadoraControllerService;
	
	@RabbitListener(queues=QUEUE_CALCULADORA_SERVICE)
    public void handleMessage(@Payload EventoResultado event) throws JsonProcessingException {
        LOG.info("EventGeneralOutlook received in calculadoraservice. Event has Json: " + new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(event));
        calculadoraControllerService.setOperacion(event);
    }
}
