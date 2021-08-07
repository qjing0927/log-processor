package com.coding.logprocessor.service;

import com.coding.logprocessor.dto.LogEntry;
import com.coding.logprocessor.model.LogEvent;
import com.coding.logprocessor.repository.LogEventRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class LogEntryService {
    private final LogEventRepository logEventRepository;
    private final ObjectMapper objectMapper;
    private final Map<String, LogEntry> logEntries = new HashMap<>();

    /***
     * Process input jsonString to LogEntry Object
     * @param jsonString
     */
    public void processFromJsonString(String jsonString) {
        LogEntry logEntry;

        try {
            logEntry = objectMapper.readValue(jsonString, LogEntry.class);
            if (logEntries.containsKey(logEntry.getId())) {
                mapToLogEntity(logEntries.get(logEntry.getId()), logEntry);
                logEntries.remove(logEntry.getId());
            } else {
                logEntries.put(logEntry.getId(), logEntry);
            }
        } catch (JsonProcessingException e) {
            log.error("Failed to parse log string - {}", jsonString);
            e.printStackTrace();
        }
    }

    /**
     * Map found log entries with same id to LogEvent
     * and save to database
     * @param existing
     * @param current
     */
    private void mapToLogEntity(LogEntry existing, LogEntry current) {
        long duration = Math.abs(existing.getTimestamp() - current.getTimestamp());
        boolean alert = duration > 4;

        LogEvent logEvent = new LogEvent(existing.getId(), duration, existing.getType(), existing.getHost(), alert);

        //write into db
        logEventRepository.save(logEvent);
    }

    /**
     * Valid if all entries in HashMap are processed, the left entries are log entry doesn't have a matched id entry.
     */
    public void validateAllLinesAreProcessed() {
        if (logEntries.size() > 0) {
            log.error("There are unmatched log entries exist in file, which are not written into db");
        } else {
            log.info("all found log events are written into db successfully");
        }
    }


}
