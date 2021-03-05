package es.rgicr76.service;

import java.util.Optional;
import java.util.function.BiFunction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.rgicr76.messaging.EventoCalcular;
import es.rgicr76.model.Operation;
import es.rgicr76.model.Operator;

@Component
public class CalculadoraBackendService {

	private static final Logger LOG = LoggerFactory.getLogger(CalculadoraBackendService.class);

	public Operator resolverNodo(Operator operator, Operation operation,
			BiFunction<java.lang.Float, java.lang.Float, java.lang.Float> func) {

		try {
		if (operator.getOperator1().getValue() != null && operator.getOperator2().getValue() != null
				&& operator.getOperation().equals(operation)) {
			operator.setValue(func.apply(operator.getOperator1().getValue(), operator.getOperator2().getValue()));
		}
		} catch (Exception e) {
			throw e;
		}

		if (operator.getOperator1() != null && operator.getOperator1().getValue() == null) {
			operator.setOperator1(resolverNodo(operator.getOperator1(), operation, func));
		}

		if (operator.getOperator2() != null && operator.getOperator2().getValue() == null) {
			operator.setOperator2(resolverNodo(operator.getOperator2(), operation, func));
		}
		return operator;
	}



	public String obtenerOperacion(Operator operator, Operation operation) {

		if (operator.getOperator1() == null && operator.getOperator2() == null && operator.getOperation() == null) {
			return "";
		}

		String parcial1 = "";
		if (operator.getOperator1() != null) {
			parcial1 = obtenerOperacion(operator.getOperator1(), operation);
			if (!"".equals(parcial1)) {
				return parcial1;
			}
		}

		String parcial2 = "";
		if (operator.getOperator2() != null) {
			parcial2 = obtenerOperacion(operator.getOperator2(), operation);
			if (!"".equals(parcial2)) {
				return parcial2;
			}
		}

		if (operator.getValue() == null && !operator.getOperation().equals(operation))
			return operator.getOperation().getName();
		return "";
	}
}
