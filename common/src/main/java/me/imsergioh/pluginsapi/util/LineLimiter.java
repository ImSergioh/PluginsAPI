package me.imsergioh.pluginsapi.util;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class LineLimiter {

    public static List<String> limitLines(List<String> lines, int maxCharsPerLine) {
        List<String> result = new ArrayList<>();

        for (String line : lines) {
            String[] words = line.split("\\s+");
            StringBuilder currentLine = new StringBuilder();

            for (String word : words) {
                if (currentLine.length() + word.length() + 1 > maxCharsPerLine) {
                    result.add(currentLine.toString());
                    currentLine = new StringBuilder();
                }

                if (!currentLine.isEmpty()) {
                    currentLine.append(" ");
                }
                currentLine.append(word);
            }

            if (!currentLine.isEmpty()) {
                result.add(currentLine.toString());
            }
        }

        return result;
    }

}
