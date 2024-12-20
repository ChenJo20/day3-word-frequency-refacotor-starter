import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WordFrequencyGame {

    public static final String SPLIT_PATTERN = "\\s+";
    public static final String LINE_BREAK = "\n";
    public static final String CALCULATE_ERROR_MESSAGE = "Calculate Error";
    public static final String RESULT_FORMATTER = "%s %d";

    public String getWordFrequency(String sentence) {
        try {
            List<WordFrequency> wordFrequencies = getInitialWordFrequencies(sentence);

            wordFrequencies = getWordFrequencies(wordFrequencies);

            return composeResult(wordFrequencies);
        } catch (Exception e) {
            return CALCULATE_ERROR_MESSAGE;
        }
    }

    private static String composeResult(List<WordFrequency> wordFrequencies) {
        return wordFrequencies.stream()
                .map(wordFrequency -> String.format(RESULT_FORMATTER, wordFrequency.getWord(), wordFrequency.getWordCount()))
                .collect(Collectors.joining(LINE_BREAK));
    }

    private List<WordFrequency> getWordFrequencies(List<WordFrequency> wordFrequencies) {
        Map<String, List<WordFrequency>> wordToWordfrequency = getWordFrequencyMap(wordFrequencies);

        return wordToWordfrequency.entrySet().stream()
                .map(entry -> new WordFrequency(entry.getKey(), entry.getValue().size()))
                .sorted(Comparator.comparing(WordFrequency::getWordCount, Comparator.reverseOrder()))
                .toList();
    }

    private static List<WordFrequency> getInitialWordFrequencies(String sentence) {
        String[] words = sentence.split(SPLIT_PATTERN);
        return Arrays.stream(words)
                .map(word -> new WordFrequency(word, 1))
                .toList();
    }

    private Map<String, List<WordFrequency>> getWordFrequencyMap(List<WordFrequency> wordFrequencies) {
        return wordFrequencies.stream()
                .collect(Collectors.groupingBy(WordFrequency::getWord));
    }


}
