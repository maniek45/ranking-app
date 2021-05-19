package com.company.ranking.rank;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.company.ranking.index.TokensIndex;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class RankCalculatorTest {

  @Test
  void shouldCalcEmptyRankForEmptyInput() {
    TokensIndex indexer = mock(TokensIndex.class);
    given(indexer.getFileNames(anyString())).willReturn(Collections.emptyIterator());
    RankCalculator rankCalculator = new RankCalculator(indexer);

    Map<String, Integer> result = rankCalculator.calc(Set.of("Ala", "ma", "kota"));

    assertEquals(Map.of(), result);
  }

  @Test
  void shouldCalcRankForNonEmptyInput() {
    TokensIndex indexer = mock(TokensIndex.class);
    given(indexer.getFileNames("Ala")).willReturn(List.of("file1", "file2").iterator());
    given(indexer.getFileNames("ma")).willReturn(List.of("file1").iterator());
    given(indexer.getFileNames("kota")).willReturn(Collections.emptyIterator());
    RankCalculator rankCalculator = new RankCalculator(indexer);

    Map<String, Integer> result = rankCalculator.calc(Set.of("Ala", "ma", "kota"));

    assertEquals(Map.of("file1", 2, "file2", 1), result);
  }
}
