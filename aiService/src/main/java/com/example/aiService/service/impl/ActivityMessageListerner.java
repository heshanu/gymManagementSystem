package com.example.aiService.service.impl;

import com.example.aiService.model.Activity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.rabbitmq.client.Channel;
import com.rabbitmq.tools.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityMessageListerner {
     @RabbitListener(queues = "activity.queue")
     public void handleMessage(Activity activity, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
         System.out.println("Received activity: " + activity);

         // DON'T acknowledge - messages will stay in queue
         // channel.basicAck(tag, false); // COMMENT THIS LINE
     }
    }
