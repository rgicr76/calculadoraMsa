package es.rgicr76.messaging;

import com.fasterxml.jackson.annotation.JsonRootName;

import es.rgicr76.model.Operator;

@JsonRootName(value="EventoCalcular")
public class EventoCalcular {

    private Operator operator;
    private String proccesId;

	public Operator getOperator() {
		return operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	public String getProccesId() {
		return proccesId;
	}

	public void setProccesId(String proccesId) {
		this.proccesId = proccesId;
	}



  }
