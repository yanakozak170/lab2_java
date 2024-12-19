import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введіть текст для аналізу:");
        String input = scanner.nextLine();

        if (input.trim().isEmpty()) {
            System.err.println("Помилка: Ви не ввели жодного тексту.");
            return;
        }

        if (!input.matches(".*[a-zA-Zа-яА-Я].*") && input.matches(".*\\d.*|.*[^a-zA-Zа-яА-Я0-9\\s.,!?].*")) {
            System.err.println("Помилка: Текст містить лише числа або некоректні символи. Введіть коректний текст.");
            return;
        }

        StringBuffer inputText = new StringBuffer(input);

        try {
            int maxSentenceCount = findMaxSentencesWithCommonWords(inputText);
            System.out.println("Максимальна кількість речень із спільними словами: " + maxSentenceCount);
        } catch (Exception e) {
            System.err.println("Сталася помилка: " + e.getMessage());
        }
    }

    public static int findMaxSentencesWithCommonWords(StringBuffer text) throws IllegalArgumentException {
        if (text == null || text.length() == 0) {
            throw new IllegalArgumentException("Текст не може бути порожнім або null.");
        }

        // Розділення тексту на речення
        String[] sentences = text.toString().split("\\.");

        // Карта для підрахунку частоти повторень у реченнях
        Map<String, Set<Integer>> wordToSentenceMap = new HashMap<>();

        for (int i = 0; i < sentences.length; i++) {
            String sentence = sentences[i].trim();

            if (sentence.isEmpty()) {
                continue;
            }

            String[] words = sentence.split("\\s+");
            for (String word : words) {
                String lowerCaseWord = word.toLowerCase().replaceAll("[^a-zA-Zа-яА-Я0-9]", "");

                if (!lowerCaseWord.isEmpty()) {
                    wordToSentenceMap.computeIfAbsent(lowerCaseWord, k -> new HashSet<>()).add(i);
                }
            }
        }

        // Знаходимо максимальну кількість речень з однаковими словами
        int maxSentencesWithCommonWords = 0;

        for (Set<Integer> sentenceIndices : wordToSentenceMap.values()) {
            if (sentenceIndices.size() > 1) { // Враховувати лише, якщо слово зустрічається більше ніж в одному реченні
                maxSentencesWithCommonWords = Math.max(maxSentencesWithCommonWords, sentenceIndices.size());
            }
        }

        return maxSentencesWithCommonWords;
    }
}
