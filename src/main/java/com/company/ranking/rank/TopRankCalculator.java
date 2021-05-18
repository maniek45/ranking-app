package com.company.ranking.rank;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TopRankCalculator {
  private static final Pattern WHITESPACE_PATTERN = Pattern.compile("\\p{javaWhitespace}+");
  private final RankCalculator rankCalculator;

  public TopRankCalculator(RankCalculator rankCalculator) {
    this.rankCalculator = rankCalculator;
  }

  public List<RankEntry> topTen(String searchInput) {
    Set<String> keys = new HashSet<>(Arrays.asList(WHITESPACE_PATTERN.split(searchInput)));
    return rankCalculator.calc(keys).entrySet().stream()
            .sorted(this::descOrderCompare)
            .limit(10)
            .map(entry -> new RankEntry(entry.getKey(), counterToPercent(entry.getValue(), keys.size())))
            .collect(Collectors.toList());
  }

  private int descOrderCompare(Entry<String, Integer> e1, Entry<String, Integer> e2) {
    return e2.getValue().compareTo(e1.getValue());
  }

  private int counterToPercent(int counter, int numOfKeys) {
    return counter * 100 / numOfKeys;
  }

  public static class RankEntry {
    private final String fileName;
    private final Integer percent;

    RankEntry(String fileName, Integer percent) {
      this.fileName = fileName;
      this.percent = percent;
    }

    public String getFileName() {
      return fileName;
    }

    public Integer getPercent() {
      return percent;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      RankEntry rankEntry = (RankEntry) o;
      return Objects.equals(fileName, rankEntry.fileName) && Objects.equals(percent, rankEntry.percent);
    }

    @Override
    public int hashCode() {
      return Objects.hash(fileName, percent);
    }

    @Override
    public String toString() {
      return "RankEntry{" +
          "fileName='" + fileName + '\'' +
          ", percent=" + percent +
          '}';
    }
  }
}
