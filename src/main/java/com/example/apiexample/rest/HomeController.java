package com.example.apiexample.rest;

import com.example.apiexample.metric.Metric;
import com.example.apiexample.metric.MetricService;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
  private final AtomicLong pingCounter  = new AtomicLong(0);

  private final AtomicLong helloCounter  = new AtomicLong(0);

  @Autowired
  private MetricService metricService;

  @GetMapping("/ping")
  private ResponseEntity<String> ping() {
    Metric countMetric = Metric.builder()
        .name("ping.count").value(1).build();
    metricService.count(countMetric);

    Metric histogramMetric = Metric.builder()
        .name("home.count")
        .value(pingCounter.incrementAndGet())
        .tag(new Metric.Tag("method", "ping"))
        .build();
    metricService.histogram(histogramMetric);

    Metric gaugeMetric = Metric.builder()
        .name("ping.gauge").value(RandomUtils.nextDouble(5, 1000))
        .build();
    metricService.gauge(gaugeMetric);

    return ResponseEntity.ok("pong");
  }

  @GetMapping("/hello")
  private ResponseEntity<String> hello() {
    Metric countMetric = Metric.builder()
        .name("hello.count").value(1).build();
    metricService.count(countMetric);

    Metric histogramMetric = Metric.builder()
        .name("home.count")
        .value(helloCounter.incrementAndGet())
        .tag(new Metric.Tag("method", "hello"))
        .build();
    metricService.histogram(histogramMetric);

    metricService.gauge(Metric.builder()
        .name("hello.gauge").value(RandomUtils.nextDouble(5, 1000)).build());
    return ResponseEntity.ok("hello world");
  }
}
