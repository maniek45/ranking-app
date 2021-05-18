package com.company.ranking.index;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Indexer {
  private final Map<String, Set<String>> map = new HashMap<>();

  public void appendToIndex(String name, Iterator<String> inputIterator) {
    while (inputIterator.hasNext()) {
      String key = inputIterator.next();
      Set<String> values = map.computeIfAbsent(key, k -> new TreeSet<>());
      values.add(name);
    }
  }

  public Iterator<String> index(String key) {
    return map.getOrDefault(key, Collections.emptySet()).iterator();
  }

  public int size() {
    return map.size();
  }
}
