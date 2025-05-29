package com.example.aiService.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {
    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.queue.name}")
    private String queue;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;
    @Bean
    public Queue activityQueue() {
        return new Queue(queue, true);
    }
    // Declare exchange
    @Bean
    public DirectExchange activityExchange() {
        return new DirectExchange(exchange);
    }

    // Bind queue to exchange
    @Bean
    public Binding activityBinding(Queue activityQueue,DirectExchange activityExchange) {
        return BindingBuilder.bind(activityQueue)
                .to(activityExchange)
                .with(routingKey);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter jsonMessageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        // Wrap original converter with logging converter
        rabbitTemplate.setMessageConverter(new LoggingMessageConverter(jsonMessageConverter));
        return rabbitTemplate;
    }
}
