package es.rgicr76.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;


public interface MessageHandler {

    public void handleMessage(EventoCalcular event) throws JsonProcessingException ;
}
