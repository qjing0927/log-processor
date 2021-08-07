package com.coding.logprocessor.service;

import com.coding.logprocessor.repository.LogEventRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;



@ExtendWith(MockitoExtension.class)
public class LogEntryServiceUnitTest {
    private LogEventRepository logEventRepository;
    private ObjectMapper objectMapper = new ObjectMapper();
    private LogEntryService logEntryService;

    @Rule
    public ExpectedException thrown= ExpectedException.none();

    @BeforeEach
    public void setup() {
        logEventRepository = Mockito.mock(LogEventRepository.class);
        logEntryService = new LogEntryService(logEventRepository, objectMapper);
    }

    @Test
    public void processFromJsonString() {
        String jsonString = "{\"id\":\"scsmbstgra\", \"state\":\"STARTED\", \"type\":\"APPLICATION_LOG\", \"host\":\"12345\", \"timestamp\":1491377495212}";
        logEntryService.processFromJsonString(jsonString);
        Mockito.verifyNoInteractions(logEventRepository);
        String nextJsonString = "{\"id\":\"scsmbstgra\", \"state\":\"FINISHED\", \"type\":\"APPLICATION_LOG\", \"host\":\"12345\", \"timestamp\":1491377495217}";
        logEntryService.processFromJsonString(nextJsonString);

        Mockito.verify(logEventRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    public void processFromJsonString_Invalid(){
        thrown.expect(JsonProcessingException.class);
        logEntryService.processFromJsonString("test123");
    }
}