package es.rgicr76.messaging;

import static es.rgicr76.messaging.Queues.QUEUE_DIVID_BACKEND;
import static es.rgicr76.messaging.Queues.QUEUE_MULTI_BACKEND;
import static es.rgicr76.messaging.Queues.QUEUE_RESTA_BACKEND;
import static es.rgicr76.messaging.Queues.QUEUE_SUMA_BACKEND;
import static es.rgicr76.messaging.Queues.QUEUE_CALCULADORA_SERVICE;
import static es.rgicr76.messaging.Queues.TOPIC_CALCULADORA;

import java.util.Arrays;
import java.util.List;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

@Configuration
@EnableRabbit
public class MessagingConfiguration implements RabbitListenerConfigurer {

    /*
     * Outbound Configuration
     */
    @Bean
    public FanoutExchange exchange() {
        return new FanoutExchange(TOPIC_CALCULADORA);
    }

    @Bean
    public Queue queueDividBackend() {
        return new Queue(QUEUE_DIVID_BACKEND, false);
    }

    @Bean
    public Queue queueMultiBackend() {
        return new Queue(QUEUE_MULTI_BACKEND, false);
    }

    @Bean
    public Queue queueRestaBackend() {
        return new Queue(QUEUE_RESTA_BACKEND, false);
    }

    @Bean
    public Queue queueSumaBackend() {
        return new Queue(QUEUE_SUMA_BACKEND, false);
    }

    @Bean
    public Queue queueCalculadoraService() {
        return new Queue(QUEUE_CALCULADORA_SERVICE, false);
    }

    @Bean
    public List<Binding> binding() {
        return Arrays.asList(
                BindingBuilder.bind(queueDividBackend()).to(exchange()),
                BindingBuilder.bind(queueMultiBackend()).to(exchange()),
                BindingBuilder.bind(queueRestaBackend()).to(exchange()),
                BindingBuilder.bind(queueSumaBackend()).to(exchange()),
                BindingBuilder.bind(queueCalculadoraService()).to(exchange()));
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /*
     * Inbound Configuration
     * We are using Annotation-Driven-Message-Listening, described here
     * http://docs.spring.io/spring-amqp/reference/htmlsingle/#async-annotation-driven
     */
    @Autowired
    public ConnectionFactory connectionFactory;

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setConcurrentConsumers(3);
        factory.setMaxConcurrentConsumers(10);
        return factory;
    }

    @Bean
    @ConditionalOnProperty(
    	    value="module.operator", 
    	    havingValue = "suma", 
    	    matchIfMissing = false)
    public MessageHandler eventResultHandler() {
        return (MessageHandler) new MessageHandlerSuma();
    }

    @Bean(name="eventResultHandler")
    @ConditionalOnProperty(
    	    value="module.operator", 
    	    havingValue = "resta", 
    	    matchIfMissing = false)
    public MessageHandler eventResultHandler2() {
        return (MessageHandler) new MessageHandlerResta();
    }

    @Bean(name="eventResultHandler")
    @ConditionalOnProperty(
    	    value="module.operator", 
    	    havingValue = "multipli", 
    	    matchIfMissing = false)
    public MessageHandler eventResultHandler3() {
        return (MessageHandler) new MessageHandlerMulti();
    }
    @Bean(name="eventResultHandler")
    @ConditionalOnProperty(
    	    value="module.operator", 
    	    havingValue = "divid", 
    	    matchIfMissing = false)
    public MessageHandler eventResultHandler4() {
        return (MessageHandler) new MessageHandlerMulti();
    }


    @Override
    public void configureRabbitListeners(
            RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(myHandlerMethodFactory());
    }

    @Bean
    public DefaultMessageHandlerMethodFactory myHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        factory.setMessageConverter(new MappingJackson2MessageConverter());
        return factory;
    }
}
