package es.rgicr76.model;


public class Operator {

	private Operator operator1;
	private Operator operator2;
	private Operation operation;
	private Float value;
	
	public Operator(Operator operator1, Operator operator2, Operation operation, float value) {
		super();
		this.operator1 = operator1;
		this.operator2 = operator2;
		this.operation = operation;
		this.value = value;
	}
	public Operator() {
		super();
	}
	public Operator getOperator1() {
		return operator1;
	}
	public void setOperator1(Operator operator1) {
		this.operator1 = operator1;
	}
	public Operator getOperator2() {
		return operator2;
	}
	public void setOperator2(Operator operator2) {
		this.operator2 = operator2;
	}
	public Operation getOperation() {
		return operation;
	}
	public void setOperation(Operation operation) {
		this.operation = operation;
	}
	public Float getValue() {
		return value;
	}
	public void setValue(Float value) {
		this.value = value;
	}


}
