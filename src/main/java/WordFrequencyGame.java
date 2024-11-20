import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class WordFrequencyGame {

    public static final String SPLIT_PATTERN = "\\s+";
    public static final String LINE_BREAK = "\n";

    public String getWordFrequency(String sentence) {
        if (sentence.split(SPLIT_PATTERN).length == 1) {
            return sentence + " 1";
        } else {
            try {
                List<WordFrequency> wordFrequencies = getInitialWordFrequencies(sentence);

                wordFrequencies = getWordFrequencies(wordFrequencies);

                return wordFrequencies.stream()
                        .map(wordFrequency -> wordFrequency.getWord() + " " + wordFrequency.getWordCount())
                        .collect(Collectors.joining(LINE_BREAK));
            } catch (Exception e) {
                return "Calculate Error";
            }
        }
    }

    private List<WordFrequency> getWordFrequencies(List<WordFrequency> wordFrequencies) {
        //get the map for the next step of sizing the same word
        Map<String, List<WordFrequency>> wordToWordfrequency = getWordFrequencyMap(wordFrequencies);
        List<WordFrequency> wordFrequencyList = new ArrayList<>();
        for (Map.Entry<String, List<WordFrequency>> entry : wordToWordfrequency.entrySet()) {
            WordFrequency wordFrequency = new WordFrequency(entry.getKey(), entry.getValue().size());
            wordFrequencyList.add(wordFrequency);
        }
        wordFrequencies = wordFrequencyList;
        wordFrequencies.sort((word, nextWord) -> nextWord.getWordCount() - word.getWordCount());
        return wordFrequencies;
    }

    private static List<WordFrequency> getInitialWordFrequencies(String sentence) {
        //split the input string with 1 to n pieces of spaces
        String[] words = sentence.split(SPLIT_PATTERN);

        List<WordFrequency> wordFrequencies = Arrays.stream(words).map(word -> new WordFrequency(word, 1)).toList();
        return wordFrequencies;
    }

    private Map<String, List<WordFrequency>> getWordFrequencyMap(List<WordFrequency> wordFrequencies) {
        Map<String, List<WordFrequency>> wordFrequencyMap = new HashMap<>();
        wordFrequencies.forEach(wordFrequency -> {
            if (!wordFrequencyMap.containsKey(wordFrequency.getWord())) {
                ArrayList frequencies = new ArrayList<>();
                frequencies.add(wordFrequency);
                wordFrequencyMap.put(wordFrequency.getWord(), frequencies);
            } else {
                wordFrequencyMap.get(wordFrequency.getWord()).add(wordFrequency);
            }
        });
//       map.computeIfAbsent(input.getValue(), k -> new ArrayList<>()).add(input);
        return wordFrequencyMap;
    }


}
