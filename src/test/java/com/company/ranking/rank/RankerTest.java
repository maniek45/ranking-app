package com.company.ranking.rank;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.company.ranking.rank.TopRankCalculator.RankEntry;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class RankerTest {

  @Test
  void shouldReturnEmptyResultOnEmptyLine() {
    RankCalculator rankCalculator = mock(RankCalculator.class);
    given(rankCalculator.calc(Set.of())).willReturn(Map.of());
    TopRankCalculator ranker = new TopRankCalculator(rankCalculator);

    List<RankEntry> topTenRanking = ranker.topTen("");

    assertEquals(List.of(), topTenRanking);
  }

  @Test
  void shouldReturnNonEmptyMapOnEmptyLine() {
    RankCalculator rankCalculator = mock(RankCalculator.class);
    given(rankCalculator.calc(Set.of("to", "be", "or", "not")))
        .willReturn(Map.of("file1", 4, "file2", 3, "file3", 2, "file4", 1));
    TopRankCalculator ranker = new TopRankCalculator(rankCalculator);

    List<RankEntry> topTenRanking = ranker.topTen("to be or not to be");

    assertEquals(
        List.of(
            new RankEntry("file1", 100),
            new RankEntry("file2", 75),
            new RankEntry("file3", 50),
            new RankEntry("file4", 25)
        ),
        topTenRanking
    );
  }
}
