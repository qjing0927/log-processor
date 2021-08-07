package com.coding.logprocessor.repository;

import com.coding.logprocessor.model.LogEvent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogEventRepository extends CrudRepository<LogEvent, String> {
}
