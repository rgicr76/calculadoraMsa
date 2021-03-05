package es.rgicr76.common;

import java.util.function.BiFunction;

import es.rgicr76.messaging.EventoCalcular;
import es.rgicr76.model.Operation;
import es.rgicr76.model.Operator;

public class ModelUtil {

    public static EventoCalcular exampleEventoCalcular() {
    	EventoCalcular eventoCalcular = new EventoCalcular();

        Operator operator = new Operator();
        operator.setOperation(Operation.ADD);
        operator.setOperator1(new Operator(null,null,null,5));
        operator.setOperator2(new Operator(null,null,null,5));
        operator.setValue(10F);
        eventoCalcular.setOperator(operator);
        return eventoCalcular;
    }
    
    
}
