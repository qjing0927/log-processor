package com.coding.logprocessor.repository;

import com.coding.logprocessor.model.LogEvent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(args="")
class LogEventRepositoryUnitTest {

    @Autowired
    private LogEventRepository logEventRepository;

    @Test
    public void whenFindingCustomerById_thenCorrect() {
        logEventRepository.save(new LogEvent("scsmbstgrc", 6L, "APPLICATION_LOG", "123456", true));
        assertThat(logEventRepository.findById("scsmbstgra")).isInstanceOf(Optional.class);
    }

    @Test
    public void whenFindingAllCustomers_thenCorrect() {
        logEventRepository.save(new LogEvent("scsmbstgrc", 3L, "", "", false));
        logEventRepository.save(new LogEvent("scsmbstgra", 4L, "APPLICATION_LOG", "", false));
        assertThat(logEventRepository.findAll()).isInstanceOf(List.class);
    }

        @Test
    public void whenSavingCustomer_thenCorrect() {
        logEventRepository.save(new LogEvent("scsmbstgrc", 6L, "APPLICATION_LOG", "123456", true));
            LogEvent logEvent = logEventRepository.findById("scsmbstgrc").orElseGet(()
                -> (new LogEvent("scsmbstgra", 3L, "", "", false)));
        assertThat(logEvent.getEventId()).isEqualTo("scsmbstgrc");
    }
}