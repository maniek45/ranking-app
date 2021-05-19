package com.company.ranking.rank;

import com.company.ranking.index.TokensIndex;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class RankCalculator {
  private final TokensIndex indexer;

  public RankCalculator(TokensIndex indexer) {
    this.indexer = indexer;
  }

  public Map<String, Integer> calc(Set<String> keys) {
    return keys.stream()
        .flatMap(key -> StreamSupport.stream(((Iterable<String>) () -> indexer.getFileNames(key)).spliterator(), false))
        .collect(Collectors.groupingBy(Function.identity(), Collectors.summingInt(e -> 1)));
  }
}
