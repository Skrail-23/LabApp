package com.hhn.labapp.usage;

public class Event {
    private String customerId;
    private String workloadId;
    private long timestamp;
    private String eventType;

    // Standard-Konstruktor (wichtig für die Serialisierung)
    public Event() {}

    // Konstruktor mit allen Argumenten
    public Event(String customerId, String workloadId, long timestamp, String eventType) {
        this.customerId = customerId;
        this.workloadId = workloadId;
        this.timestamp = timestamp;
        this.eventType = eventType;
    }

    // Getter und Setter
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getWorkloadId() {
        return workloadId;
    }

    public void setWorkloadId(String workloadId) {
        this.workloadId = workloadId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
}
