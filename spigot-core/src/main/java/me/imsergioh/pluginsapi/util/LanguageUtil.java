package me.imsergioh.pluginsapi.util;

import me.imsergioh.pluginsapi.handler.LanguagesHandler;
import me.imsergioh.pluginsapi.language.Language;

import java.util.List;

public class LanguageUtil {

    public static String parse(Language language, String message) {
        if (language == null) return message;
        if (!message.contains("<lang.")) return message;
        return get(language, message);
    }

    private static String get(Language language, String message) {
        String[] args = message.split(" ");
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (!arg.contains("<lang.")) {
                continue;
            }
            String messageHolder = arg.split("\\.")[1];
            String path = arg.replaceAll("^.*?\\.([^>]+)>$", "$1");
            if (path.startsWith(messageHolder + ".")) path = path.replace(messageHolder + ".", "");
            Object object = LanguagesHandler
                    .get(language)
                    .get(messageHolder)
                    .get(path);

            if (object instanceof String) {
                args[i] = (String) object;
            } else if (object instanceof List) {
                StringBuilder stringBuilder = new StringBuilder();
                List<?> list = (List<?>) object;
                for (Object o : list) {
                    stringBuilder.append(o.toString() + "\n");
                }
                args[i] = stringBuilder.toString();
            } else {
                args[i] = object.toString();
            }
        }
        return parse(language, String.join(" ", args));
    }

}
