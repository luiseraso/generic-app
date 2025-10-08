package com.example.di.services;

import com.example.di.models.AuditDetails;
import org.junit.Test;
import org.mockito.Mockito;

import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class EventServiceTest {

    EventService eventService;

    // AAA Pattern: Arrange/Act/Assert : Organizar/Actuar/Afirmar
    @Test
    public void sendAuditDetails_kafkaEnabled() {
        // Arrange
        KafkaTemplate<String, String> kafkaTemplate = Mockito.mock(KafkaTemplate.class); // Creando el mock de esa clase.
        EventService eventService = new EventService(kafkaTemplate, true);
        AuditDetails auditDetails = new AuditDetails("user1", "role1");

        // Act
        eventService.sendAuditDetails(auditDetails);

        // Assert
        verify(kafkaTemplate, times(1)).send(anyString(), anyString());
    }

    @Test
    public void sendAuditDetails_kafkaDisabled() {
        // Arrange
        KafkaTemplate<String, String> kafkaTemplate = Mockito.mock(KafkaTemplate.class);
        EventService eventService = new EventService(kafkaTemplate, false);
        AuditDetails auditDetails = new AuditDetails("user1", "role1");

        // Act
        eventService.sendAuditDetails(auditDetails);

        // Assert
        verify(kafkaTemplate, times(0)).send(anyString(), anyString());
    }

    // Mock: Simula ser la clase real. Es un esqueleto de la clase.

    // Tarea.
    // Hacer el flujo que falta. Para probar la excepción.
    // https://www.baeldung.com/mockito-mock-nested-method-calls


}
