package com.coding.logprocessor.dto;

import com.coding.logprocessor.constants.State;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogEntry {
    private String id;
    private State state;
    private long timestamp;
    private String type;
    private String host;
}
