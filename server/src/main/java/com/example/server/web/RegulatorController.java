package com.example.server.web;

import com.example.server.service.RegulatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = RegulatorController.REST_API, produces = APPLICATION_JSON_VALUE)
public class RegulatorController {

    public static final String REST_API = "/api/v1/regulator/";

    @Autowired
    RegulatorService regulatorService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void setTemperature(@RequestHeader(name = "test") @NumberFormat String number) {
        regulatorService.setTemperature(number);
    }

    @GetMapping
    public ResponseEntity<List<Float>> getAll() {
        return ResponseEntity.ok(regulatorService.getAll());
    }

    @GetMapping("current-temperature")
    public ResponseEntity<Float> getCurrentTemperature() {
        Float currentTemperature = regulatorService.getCurrentTemperature();
        return ResponseEntity.ok(currentTemperature);
    }

    @GetMapping("last-temperature")
    public ResponseEntity<Float> getLastWithShift(@RequestParam(name = "shift") Integer shift, @RequestParam(name = "count") Integer count) {
        Float temperatureWithShift = regulatorService.getTemperatureWithShift(shift, count);
        return ResponseEntity.ok(temperatureWithShift);

    }

    @PostMapping("clear")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void clearList() {
        regulatorService.clearTemperatureList();
    }
}
