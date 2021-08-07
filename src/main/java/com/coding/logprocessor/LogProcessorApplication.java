package com.coding.logprocessor;

import com.coding.logprocessor.dto.LogEntry;
import com.coding.logprocessor.service.LogEntryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Stream;

@SpringBootApplication
@Slf4j
@RequiredArgsConstructor
public class LogProcessorApplication implements CommandLineRunner {
    private final LogEntryService logEntryService;

    public static void main(String[] args) {
        SpringApplication.run(LogProcessorApplication.class, args);
    }

    @Override
    public void run(String[] args) {
        log.info("EXECUTING Started");
        if (Objects.isNull(args) || args.length != 1) {
            log.error("File path is not correctly provided.");
            throw new RuntimeException("Invalid File Path Parameter");
        }

        try (Stream<String> lines = Files.lines(Paths.get(args[0]))) {
            lines.forEach(logEntryService::processFromJsonString);
        } catch (InvalidPathException e) {
            log.error("File path is invalid.");
            e.printStackTrace();
        } catch (Exception e) {
            log.error("error in reading file");
            e.printStackTrace();
        }

        logEntryService.validateAllLinesAreProcessed();
    }
}
