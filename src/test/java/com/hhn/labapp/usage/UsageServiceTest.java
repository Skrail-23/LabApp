package com.hhn.labapp.usage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;

public class UsageServiceTest {

    private UsageService usageService;

    @BeforeEach
    public void setUp() {
        usageService = new UsageService();
    }

    @Test
    public void testProcessEvents_successfulStartStop() {
        // Erstelle Beispiel-Events
        Event startEvent = new Event("customer1", "workload1", 1000L, "start");
        Event stopEvent = new Event("customer1", "workload1", 5000L, "stop");

        // Führe die Methode processEvents aus
        usageService.processEvents(Arrays.asList(startEvent, stopEvent));

        // Ergebnisse prüfen
        List<Result> results = usageService.getResults();
        assertEquals(1, results.size());  // Es sollte genau ein Result geben
        assertEquals("customer1", results.get(0).getCustomerId());  // Der Kunde sollte customer1 sein
        assertEquals(4000L, results.get(0).getTotalTime());  // Laufzeit sollte 5000 - 1000 = 4000 sein
    }

    @Test
    public void testProcessEvents_missingStartEvent() {
        // Erstelle ein Stop-Event ohne vorheriges Start-Event
        Event stopEvent = new Event("customer1", "workload1", 5000L, "stop");

        // Führe die Methode processEvents aus
        usageService.processEvents(Arrays.asList(stopEvent));

        // Ergebnisse prüfen
        List<Result> results = usageService.getResults();
        assertEquals(0, results.size());  // Es sollte kein Result geben, da kein Start-Event vorhanden ist
    }

    @Test
    public void testProcessEvents_duplicateStartEvent() {
        // Erstelle doppelte Start-Events und ein Stop-Event
        Event startEvent1 = new Event("customer1", "workload1", 1000L, "start");
        Event startEvent2 = new Event("customer1", "workload1", 2000L, "start");  // Doppeltes Start-Event
        Event stopEvent = new Event("customer1", "workload1", 5000L, "stop");

        // Führe die Methode processEvents aus
        usageService.processEvents(Arrays.asList(startEvent1, startEvent2, stopEvent));

        // Ergebnisse prüfen
        List<Result> results = usageService.getResults();
        assertEquals(1, results.size());  // Es sollte genau ein Result geben
        assertEquals("customer1", results.get(0).getCustomerId());  // Der Kunde sollte customer1 sein
        assertEquals(3000L, results.get(0).getTotalTime());  // Laufzeit sollte 5000 - 2000 = 3000 sein
    }
}
