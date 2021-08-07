package com.coding.logprocessor.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "log_event")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogEvent {
    @Id
    @Column(nullable = false, name = "event_id")
    private String eventId;

    @Column(nullable = false, name = "event_duration")
    private Long eventDuration;

    @Column
    private String type;

    @Column
    private String host;

    @Column(nullable = false)
    private boolean alert;
}
