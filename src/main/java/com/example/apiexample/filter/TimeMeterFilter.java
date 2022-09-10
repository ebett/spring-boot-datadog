package com.example.apiexample.filter;

import com.example.apiexample.metric.Metric;
import com.example.apiexample.metric.Metric.Tag;
import com.example.apiexample.metric.MetricService;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Sends to datadog the time taken by the HomeController endpoints.
 * Metric: home.time_taken
 * Tags: path
 */
@Slf4j
@Component
public class TimeMeterFilter extends OncePerRequestFilter {

  private final MetricService metricService;

  @Autowired
  public TimeMeterFilter(final MetricService metricService) {
    this.metricService = metricService;
  }

  @Override
  protected void doFilterInternal(final HttpServletRequest request,
      final HttpServletResponse response, final FilterChain filterChain) {
    try {
      final long t1 = System.currentTimeMillis();
      filterChain.doFilter(request, response);

      final String path = request.getRequestURI();
      final long t2 = System.currentTimeMillis() - t1;
      log.trace("REQ: {} time taken: {} ms", path, t2);

      metricService.time(Metric.builder()
          .name("home.time_taken").value(t2)
          .tag(new Tag("path", path)).build());
    } catch (Throwable e) {
      logger.error("", e);
    }
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    final String path = request.getRequestURI();
    return !StringUtils.defaultString(path).contains("hello") &&
        !StringUtils.defaultString(path).contains("ping");
  }

}