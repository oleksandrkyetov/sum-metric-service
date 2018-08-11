package code.challenge.controller;

import code.challenge.dto.MetricRequestDto;
import code.challenge.dto.MetricResponseDto;
import code.challenge.service.MetricService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/metric")
public class MetricController {

    @Autowired
    private MetricService metricService;

    @RequestMapping(path = "/{key}", method = RequestMethod.POST)
    public ResponseEntity<?> postMetricForKey(
            @PathVariable("key") final String key,
            @RequestBody @Valid final MetricRequestDto metricRequestDto
    ) {
        // Response is slightly modified from the requested one to return current calculated value of the metric
        return ResponseEntity.status(HttpStatus.OK)
                .body(new MetricResponseDto().setValue(metricService.put(key, metricRequestDto.getValue())));
    }

    @RequestMapping(path = "/{key}/sum", method = RequestMethod.GET)
    public ResponseEntity<?> getMetricSumForKey(
            @PathVariable("key") final String key
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new MetricResponseDto().setValue(metricService.calculate(key)));
    }

}
