package com.hhn.labapp.usage;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/v1")
public class UsageController {

    private final UsageService usageService;

    public UsageController(UsageService usageService) {
        this.usageService = usageService;
    }

    // POST-Methode zum Hinzufügen von Events
    @PostMapping("/dataset")
    public void addEvents(@RequestBody List<Event> events) {
        System.out.println("Events received: " + events.size());
        usageService.processEvents(events);
    }


    // GET-Methode zum Abrufen der verarbeiteten Ergebnisse
    @GetMapping("/result")
    public List<Result> getResults() {
        return usageService.getResults(); // Rückgabe der Ergebnisse
    }
}
