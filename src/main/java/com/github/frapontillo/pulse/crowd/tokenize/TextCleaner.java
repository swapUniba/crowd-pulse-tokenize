package com.github.frapontillo.pulse.crowd.tokenize;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple text cleaner based on fixed and custom regexes from {@link TokenizerConfig}.
 *
 * @author Francesco Pontillo
 */
public class TextCleaner {
    private List<String> allRegexes;

    public TextCleaner(TokenizerConfig config) {
        allRegexes = new ArrayList<>();
        if (config == null) {
            return;
        }
        if (config.getRegexes() != null) {
            allRegexes.addAll(config.getRegexes());
        }
        if (config.getMinChars() != null && config.getMinChars() > 1) {
            allRegexes.add("\\b\\w{1," + (config.getMinChars() - 1) + "}\\b");
        }
        if (config.isUrls()) {
            allRegexes.add("(?:(?:https?|ftp):\\/\\/)(?:\\S+(?::\\S*)?@)?(?:(?!(?:10|127)(?:\\" +
                    ".\\d{1,3}){3})(?!(?:169\\.254|192\\.168)(?:\\.\\d{1,3}){2})(?!172\\." +
                    "(?:1[6-9]|2\\d|3[0-1])(?:\\.\\d{1,3}){2})" +
                    "(?:[1-9]\\d?|1\\d\\d|2[01]\\d|22[0-3])(?:\\.(?:1?\\d{1,2}|2[0-4]\\d|25[0-5])" +
                    "){2}(?:\\.(?:[1-9]\\d?|1\\d\\d|2[0-4]\\d|25[0-4]))|(?:" +
                    "(?:[a-z\\u00a1-\\uffff0-9]-*)*[a-z\\u00a1-\\uffff0-9]+)(?:\\." +
                    "(?:[a-z\\u00a1-\\uffff0-9]-*)*[a-z\\u00a1-\\uffff0-9]+)*(?:\\." +
                    "(?:[a-z\\u00a1-\\uffff]{2,}))\\.?)(?::\\d{2,5})?(?:[/?#]\\S*)?");

            allRegexes.add("(http|ftp)[^\\s]+");
        }
        if (config.isHashtags()) {
            allRegexes.add("#([A-zÀ-ÿ0-9])+");
        }
        if (config.isMentions()) {
            allRegexes.add("@([A-zÀ-ÿ0-9])+");
        }
        if (config.isNumbers()) {
            allRegexes.add("-?\\d*(\\.\\d+)?");
        }
    }

    /**
     * Clean the given text using the regular expressions passed in the constructor.
     *
     * @param text The text to clean.
     * @return A new clean {@link String}.
     */
    public String clean(String text) {

        text = text.toLowerCase();

        String regex = "([_…+.,!$%^&*();\\/|<>\"':\\?=%\\[\\]èé-])+"; //regex che elimina i caratteri speciali incondizionatamente
        allRegexes.add(regex);

        for (String it : allRegexes) {
            text = text.replaceAll(it, "");
        }
        return text;
    }
}
