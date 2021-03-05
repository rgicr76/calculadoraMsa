package es.rgicr76.messaging;

import static es.rgicr76.messaging.Queues.QUEUE_MULTI_BACKEND;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;

import es.rgicr76.model.Operation;
import es.rgicr76.model.Response;
import es.rgicr76.service.CalculadoraBackendService;

@Component
public class MessageHandlerMulti implements MessageHandler {

	private static final Logger LOG = LoggerFactory.getLogger(MessageHandlerMulti.class);

	@Autowired
	private CalculadoraBackendService calculadoraBackendService;

	@Autowired
	private MessageSender messageSender;

	@RabbitListener(queues = QUEUE_MULTI_BACKEND)
	public void handleMessage(@Payload EventoCalcular event) throws JsonProcessingException {
		LOG.info("EventoCalcular suma received");

		event.setOperator(calculadoraBackendService.resolverNodo(event.getOperator(), Operation.MUL,
				(value1, value2) -> value1 * value2));

		String siguienteOperacion = calculadoraBackendService.obtenerOperacion(event.getOperator(), Operation.MUL);

		if (!siguienteOperacion.equals("")) {
			QueueDirector.process(QueueDirector.Queue.valueOf(siguienteOperacion).getQueue(), event,
					(a1, a2) -> messageSender.sendMessage(a1, a2));
		} else {
			QueueDirector.process(QueueDirector.Queue.RES.getQueue(), 
					new EventoResultado(event.getOperator(), new Response(event.getProccesId())),
					(a1, a2) -> messageSender.sendMessage(a1, a2));
		}
	}

}
