package com.example.apiexample.metric;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.util.CollectionUtils.isEmpty;

import com.example.apiexample.metric.Metric.Tag;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class MetricTest {

  @Test
  void builder_noTags() {
    Metric metric = Metric.builder()
        .name("test").value(10)
        .build();

    assertNotNull(metric);
    assertEquals("test", metric.getName());
    assertEquals(10, metric.getValue());
    assertTrue(isEmpty(metric.getTags()));
  }

  @Test
  void builder_withTags() {
    Metric metric = Metric.builder()
        .name("test").value(10)
        .tag(new Tag("tag1", "v1"))
        .tag("tag2:v2")
        .build();

    assertNotNull(metric);
    assertEquals("test", metric.getName());
    assertEquals(10, metric.getValue());
    assertEquals(2, metric.getTags().size());
    assertEquals("tag1:v1,tag2:v2", metric.getTags().stream()
        .collect(Collectors.joining(",")));
  }
}