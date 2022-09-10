package com.example.apiexample.metric;

import com.timgroup.statsd.StatsDClient;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StatsDMetricService implements MetricService {

  @Autowired
  private StatsDClient statsDClient;

  public void count(final Metric metric) {
    sendMetric(metric, MetricType.COUNT);
  }

  public void gauge(final Metric metric) {
    sendMetric(metric, MetricType.GAUGE);
  }

  public void time(final Metric metric) {
    sendMetric(metric, MetricType.TIME);
  }

  @Override
  public void histogram(final Metric metric) {
    sendMetric(metric, MetricType.HISTOGRAM);
  }

  private void sendMetric(final Metric metric, final MetricType metricType) {
    if (metric == null) {
      throw new IllegalArgumentException("No metric was provided.");
    }

    final String methodName = metricType.name().toLowerCase();
    final List<String> tags = Optional.ofNullable(metric.getTags()).orElse(Collections.emptyList());
    final Number value = Optional.ofNullable(metric.getValue()).orElse(0l);
    final boolean isLong = value instanceof Long;

    Method method;
    try {
      method = statsDClient.getClass()
          .getMethod(methodName, String.class, isLong ? long.class : double.class, String[].class);
    } catch (NoSuchMethodException e) {
      log.error("NoSuchMethodException", e);
      return;
    }

    try {
      method.invoke(statsDClient, metric.getName(), value, tags.stream().toArray(String[]::new));
    } catch (Throwable e) {
      log.error("", e);
    }
  }
}
