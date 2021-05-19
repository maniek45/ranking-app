## Ranking App
Command-line driven text search engine.

### Build
gradlew clean build

### Run
java -jar build/libs/ranking-SNAPSHOT.jar <directoryContainingTextFiles>

### Prerequisites

* JDK 11

## Design
The application contains the following components:

### Tokenization
It takes as an input the list of text files. The input files are read and tokens (words) are extracted.
Extracting tokens uses java.util.Scanner class and its default definition of token delimiting pattern.

### Tokens indexing
The input is each parsed file name and all its extracted tokens. The index contains mapping of each extracted token
to the set of file names where it is present. The set of file names contains each file only once, even the token (word)
is repeated in the same file.

### Ranking
The input for ranking component is the line of text containing words to search for. This line is than split into
words (tokens) the same way as in the Tokenization component. Then the token index is used to count number of words
that have been found in each text file. This count and number of all searched words is enough to calculate
the percent of occurrence for each file.

### Improvements

* Exceptions/errors - strategy to handle (now app just exits on IO exceptions with stack trace)
* Parallel input files parsing - performance (speed up app startup/init time)
* Extracting command line parsing/searching loop as separate class - make it testable (std in/out as dependencies) 
