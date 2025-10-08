package com.example.di.services;

import com.example.di.models.AuditDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    private static final Logger log = LoggerFactory.getLogger(EventService.class);
    private static final String AUDIT_TOPIC = "devs4j-topic";

    @Autowired(required = false)
    private KafkaTemplate<Integer, String> kafkaTemplate;

    private final ObjectMapper mapper = new ObjectMapper();
    
    @Value("${application.kafka.enabled:false}")
    public boolean kafkaEnabled;
    
    public void sendAuditDetails(AuditDetails details) {
        try {
            if(kafkaEnabled) {
                kafkaTemplate.send(AUDIT_TOPIC, mapper.writeValueAsString(details));
            } else {
                log.info("Publish to topic: {}. Kafka is disabled!", AUDIT_TOPIC);
            }
        } catch (Exception e) {
            log.error("Error sending the message to topic: {}", AUDIT_TOPIC, e);
        }
    }
}
