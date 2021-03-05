package es.rgicr76.model;

public enum Operation {

	ADD("ADD"),
	SUB("SUB"),
	MUL("MUL"),
	DIV("DIV");
    
    private String name;
    
    private Operation(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
}
