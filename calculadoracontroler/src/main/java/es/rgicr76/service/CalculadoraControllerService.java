package es.rgicr76.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import es.rgicr76.messaging.EventoResultado;

@Component
public class CalculadoraControllerService {

	private static final Logger LOG = LoggerFactory.getLogger(CalculadoraControllerService.class);

	@Cacheable("operaciones")
	public EventoResultado obtenerOperacion(String processId) {

		return null;

	}

	@CachePut(value = "operaciones", key="#eventoResultado.response.processId")
	public EventoResultado setOperacion(EventoResultado eventoResultado) {

		return eventoResultado;

	}

}
