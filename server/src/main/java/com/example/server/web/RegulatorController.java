package com.example.server.web;

import com.example.server.service.RegulatorService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Operation(summary = "Set new temperature")
    @PostMapping
    public ResponseEntity<List<Float>> setTemperature(@RequestParam(name = "temperature") String temperature) {
        List<Float> body = regulatorService.setTemperature(temperature);
        return ResponseEntity.ok(body);
    }

    @Operation(summary = "Retrieve one of the last values")
    @GetMapping
    public ResponseEntity<List<Float>> getLastWithShift(
            @RequestParam(name = "shift", required = false, defaultValue = "0") Integer shift,
            @RequestParam(name = "count", required = false, defaultValue = "0") Integer count
    ) {
        List<Float> temperatureWithShift = regulatorService.getValueWithShift(shift, count);
        return ResponseEntity.ok(temperatureWithShift);

    }

    @Operation(summary = "Retrieve current temperature")
    @GetMapping("current-temperature")
    public ResponseEntity<Float> getCurrentTemperature() {
        Float currentTemperature = regulatorService.getCurrentTemperature();
        return ResponseEntity.ok(currentTemperature);
    }

    @Operation(summary = "Clear the temperature list")
    @PostMapping("clear")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void clearList() {
        regulatorService.clearTemperatureList();
    }
}
