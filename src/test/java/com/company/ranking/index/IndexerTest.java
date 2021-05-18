package com.company.ranking.index;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.util.List;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

public class IndexerTest {

  Indexer indexer = new Indexer();

  @Test
  void shouldBuildIndex() {
    indexer.appendToIndex("first", new Scanner("Ala ma kota"));
    indexer.appendToIndex("second", new Scanner("Ala ma psa"));

    assertEquals(4, indexer.size());
    assertAll(
        () -> assertIterableEquals(List.of("first", "second"), (Iterable<String>) () -> indexer.index("Ala")),
        () -> assertIterableEquals(List.of("first", "second"), (Iterable<String>) () -> indexer.index("ma")),
        () -> assertIterableEquals(List.of("first"), (Iterable<String>) () -> indexer.index("kota")),
        () -> assertIterableEquals(List.of("second"), (Iterable<String>) () -> indexer.index("psa"))
    );
  }

}
