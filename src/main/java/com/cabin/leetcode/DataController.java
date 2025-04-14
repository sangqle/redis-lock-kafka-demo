package com.cabin.leetcode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/data")
public class DataController {

    private final DataService dataService;

    @Autowired
    public DataController(DataService dataService) {
        this.dataService = dataService;
    }

    @PostMapping("/update")
    public String updateData(@RequestParam String requestId, @RequestParam int delta) {
        // This endpoint triggers the update logic with distributed locking
        dataService.updateSharedData(requestId, delta);
        return "Update attempted by " + requestId;
    }

    @GetMapping("/current")
    public String currentData() {
        return "Current shared data: " + dataService.getSharedData();
    }
}
