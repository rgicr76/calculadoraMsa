package es.rgicr76.messaging;

import com.fasterxml.jackson.annotation.JsonRootName;

import es.rgicr76.model.Operator;
import es.rgicr76.model.Response;

@JsonRootName(value="EventoResultado")
public class EventoResultado {

    private Operator operator;
    
    private Response response;



	public EventoResultado() {
		super();
	}

	public EventoResultado(Operator operator, Response response) {
		super();
		this.operator = operator;
		this.response = response;
	}

	public Operator getOperator() {
		return operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

  }
