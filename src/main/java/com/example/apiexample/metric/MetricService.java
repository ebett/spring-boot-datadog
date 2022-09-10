package com.example.apiexample.metric;

public interface MetricService {

  void count(Metric record);

  void gauge(Metric record);

  void time(Metric record);

  void histogram(Metric record);
}
