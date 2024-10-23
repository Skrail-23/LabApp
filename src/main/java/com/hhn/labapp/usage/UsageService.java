package com.hhn.labapp.usage;

import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class UsageService {

    // Speichert die Gesamtnutzungszeit pro Kunde
    private final Map<String, Long> customerUsage = new HashMap<>();
    // Speichert die Startzeitstempel für jede Workload
    private final Map<String, Long> startTimestamps = new HashMap<>();

    // Verarbeitung der Events
    public void processEvents(List<Event> events) {
        for (Event event : events) {
            // Verarbeitung der "start"-Events
            if ("start".equals(event.getEventType())) {
                handleStartEvent(event);
            }
            // Verarbeitung der "stop"-Events
            else if ("stop".equals(event.getEventType())) {
                handleStopEvent(event);
            }
        }
    }

    // Separater Handler für Start-Events
    private void handleStartEvent(Event event) {
        String workloadId = event.getWorkloadId();
        if (startTimestamps.containsKey(workloadId)) {
            System.err.println("Warnung: Doppelte Start-Event für Workload " + workloadId + " erkannt. Ignoriere das vorherige Start-Event.");
        }
        // Speichere den Startzeitpunkt
        startTimestamps.put(workloadId, event.getTimestamp());
    }

    // Separater Handler für Stop-Events
    private void handleStopEvent(Event event) {
        String workloadId = event.getWorkloadId();
        Long startTime = startTimestamps.remove(workloadId); // Entfernt den Startzeitpunkt
        if (startTime != null) {
            // Berechne die Gesamtnutzungszeit
            long runtime = event.getTimestamp() - startTime;
            // Füge die Laufzeit der Gesamtnutzungszeit des Kunden hinzu
            customerUsage.merge(event.getCustomerId(), runtime, Long::sum);
        } else {
            System.err.println("Fehler: Stop-Event ohne entsprechendes Start-Event für Workload " + workloadId + " erkannt. Ignoriere das Stop-Event.");
        }
    }

    // Rückgabe der verarbeiteten Ergebnisse
    public List<Result> getResults() {
        List<Result> results = new ArrayList<>();
        for (Map.Entry<String, Long> entry : customerUsage.entrySet()) {
            Result result = new Result();
            result.setCustomerId(entry.getKey());
            result.setTotalTime(entry.getValue());
            results.add(result);
        }
        return results;
    }
}
