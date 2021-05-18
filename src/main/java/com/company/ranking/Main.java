package com.company.ranking;

import com.company.ranking.index.Indexer;
import com.company.ranking.rank.RankCalculator;
import com.company.ranking.rank.TopRankCalculator;
import com.company.ranking.rank.TopRankCalculator.RankEntry;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class Main {
  private static final String QUIT_COMMAND = ":quit";

  public static void main(String[] args) throws IOException {
    if (args.length < 1) {
      System.err.println("Usage: java -jar <jar_name> " + Main.class.getName() + " <dir_with_text_files>");
      System.exit(-1);
    }

    Indexer indexer = new Indexer();

    Path pathToDirWithTextFiles = Paths.get(args[0]);
    try (DirectoryStream<Path> fileInDirStream = Files.newDirectoryStream(pathToDirWithTextFiles)) {
      int filesReadCounter = 0;
      for (Path pathToFile : fileInDirStream) {
        indexer.appendToIndex(pathToFile.toFile().getName(), new Scanner(pathToFile));
        filesReadCounter++;
      }
      System.out.printf("%d files read in directory %s%n", filesReadCounter, pathToDirWithTextFiles);
    }

    TopRankCalculator rankCalculator = new TopRankCalculator(new RankCalculator(indexer));
    Scanner stdInputScanner = new Scanner(System.in);

    while (true) {
      System.out.print("search>");
      String input = stdInputScanner.nextLine();
      if (QUIT_COMMAND.equals(input)) {
        break;
      }
      List<RankEntry> rankEntries = rankCalculator.topTen(input);
      printOutput(rankEntries);
    }
  }

  private static void printOutput(List<RankEntry> rankEntries) {
    if (rankEntries.isEmpty()) {
      System.out.println("no matches found");
    } else {
      for (RankEntry rankEntry : rankEntries) {
        System.out.printf("%s : %s%%%n", rankEntry.getFileName(), rankEntry.getPercent());
      }
    }
  }
}
