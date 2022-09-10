package com.example.apiexample.metric;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Builder(builderClassName = "Builder")
@Getter
public class Metric {
  @NonNull
  private String name;

  @NonNull
  private Number value;

  private List<String> tags;

  @RequiredArgsConstructor
  @Getter
  @NonNull
  public static final class Tag {
    private final String name;
    private final String value;

    @Override
    public String toString() {
      return name + ":" + value;
    }
  }

  public static final class Builder {
    private List<String> tags = new ArrayList<>();

    public Builder withTags(final @NonNull List<Tag> theTags) {
      theTags.forEach(tag -> tags.add(tag.toString()));
      return this;
    }

    public Builder tag(final @NonNull String tag) {
      tags.add(tag);
      return this;
    }

    public Builder tag(final @NonNull Tag tag) {
      tags.add(tag.toString());
      return this;
    }
  }
}
