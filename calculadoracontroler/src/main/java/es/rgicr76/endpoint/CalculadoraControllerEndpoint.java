package es.rgicr76.endpoint;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import es.rgicr76.messaging.EventoCalcular;
import es.rgicr76.messaging.EventoResultado;
import es.rgicr76.messaging.MessageSender;
import es.rgicr76.messaging.QueueDirector;
import es.rgicr76.model.Operation;
import es.rgicr76.model.Operator;
import es.rgicr76.model.Response;
import es.rgicr76.service.CalculadoraControllerService;

@RestController
public class CalculadoraControllerEndpoint {

	private static final Logger LOG = LoggerFactory.getLogger(CalculadoraControllerEndpoint.class);

	@Autowired
	private MessageSender messageSender;

	@Autowired
	private CalculadoraControllerService calculadoraControllerService;

	@PostMapping("/operate")
	public Response operate(@RequestBody Operator operator) throws JsonProcessingException {

		LOG.info("CalculadoraController invoked for Operator	");

		EventoCalcular eventoCalcular = new EventoCalcular();
		eventoCalcular.setOperator(operator);
		eventoCalcular.setProccesId(UUID.randomUUID().toString());
		QueueDirector.process(QueueDirector.Queue.valueOf(operator.getOperation().getName()).getQueue(), eventoCalcular,
				(a1, a2) -> messageSender.sendMessage(a1, a2));

		return new Response(eventoCalcular.getProccesId());
	}

	@GetMapping("/operate/{processId}/")
	public Operator getOperateResult(@PathVariable String processId) throws JsonProcessingException {
		LOG.info("getOperateResult() called, sending Message to 'calculadoraservice:queue'");
		EventoResultado pp = calculadoraControllerService.obtenerOperacion(processId);
		return pp.getOperator();

	}
}
