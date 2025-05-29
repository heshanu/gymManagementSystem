package com.example.aiService.config;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

import java.nio.charset.StandardCharsets;

public class LoggingMessageConverter implements MessageConverter {

    private final MessageConverter delegate;
    private static final int MAX_BODY_LOG_LENGTH = 10240; // 10KB

    public LoggingMessageConverter(MessageConverter delegate) {
        this.delegate = delegate;
    }

    @Override
    public Message toMessage(Object object, MessageProperties messageProperties) throws MessageConversionException {
        Message message = delegate.toMessage(object, messageProperties);
        return new LoggingMessage(message);
    }

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        return delegate.fromMessage(message);
    }

    private static class LoggingMessage extends Message {
        private final Message delegate;

        public LoggingMessage(Message delegate) {
            super(delegate.getBody(), delegate.getMessageProperties());
            this.delegate = delegate;
        }

        @Override
        public String toString() {
            if (getBody() == null) {
                return "(Body: null)";
            }
            if (getBody().length > MAX_BODY_LOG_LENGTH) {
                return delegate.toString(); // Default representation for large bodies
            }
            String charset = getMessageProperties().getContentEncoding();
            if (charset == null) charset = StandardCharsets.UTF_8.name();
            try {
                String bodyStr = new String(getBody(), charset);
                return "(Body:'" + bodyStr + "' " + getMessageProperties() + ")";
            } catch (Exception e) {
                return delegate.toString(); // Fallback to default on error
            }
        }
    }
}