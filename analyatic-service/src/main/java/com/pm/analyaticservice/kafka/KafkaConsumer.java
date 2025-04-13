package com.pm.analyaticservice.kafka;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;

@Service
public class KafkaConsumer {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "patient-event", groupId = "analyatic-service")
    public void consumeEvent(byte[] event) {

        try {
            PatientEvent patientEvent = PatientEvent.parseFrom(event);
            log.info("Received event from Kafka: {}", patientEvent);
        } catch (Exception e) {


            System.out.println("Failed to parse event: " + e.getMessage());
        }



    }
}
