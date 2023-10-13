package com.amigoscode.notification;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationConfig {


    @Value("${rabbitmq.exchanges.internal}")
    private String internalExcange;

    @Value("${rabbitmq.queues.notification}")
    private String notificationQueue;

    @Value("${rabbitmq.routing-keys.internal-notification}")
    private String internalNotificationRoutingKeys;

    @Bean
    public TopicExchange internalTopicExchange() {
        return new TopicExchange(this.getInternalExcange());
    }

    @Bean
    public Queue notificationQueue() {
        return new Queue(this.getNotificationQueue());
    }

    @Bean
    public Binding internalToNotificationBinding(){
        return BindingBuilder.bind(notificationQueue())
                .to(internalTopicExchange())
                .with(this.internalNotificationRoutingKeys);
    }


    public String getInternalNotificationRoutingKeys() {
        return internalNotificationRoutingKeys;
    }

    public String getNotificationQueue() {
        return notificationQueue;
    }

    public String getInternalExcange() {
        return internalExcange;
    }
}
