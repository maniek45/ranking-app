package com.company.ranking.index;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class TokensIndex {
  private final Map<String, Set<String>> tokenToFileNames = new HashMap<>();

  public void appendToIndex(String fileName, Iterator<String> tokenIterator) {
    while (tokenIterator.hasNext()) {
      String token = tokenIterator.next();
      Set<String> values = tokenToFileNames.computeIfAbsent(token, k -> new HashSet<>());
      values.add(fileName);
    }
  }

  public Iterator<String> getFileNames(String token) {
    return tokenToFileNames.getOrDefault(token, Collections.emptySet()).iterator();
  }

  public int size() {
    return tokenToFileNames.size();
  }
}
