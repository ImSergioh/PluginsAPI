package me.imsergioh.pluginsapi.handler;

import me.imsergioh.pluginsapi.language.Language;
import me.imsergioh.pluginsapi.language.LanguageHolder;

import java.util.Collection;
import java.util.HashMap;
import java.util.function.Consumer;

public class LanguagesHandler {

    private static final HashMap<Language, LanguageHolder> holders = new HashMap<>();

    public static LanguageHolder register(Language language) {
        LanguageHolder holder = new LanguageHolder(language);
        holders.put(language, holder);
        return holder;
    }

    public static void forEach(Consumer<LanguageHolder> languageHolder) {
        for (LanguageHolder holder : holders.values()) {
            if (holder == null) continue;
            languageHolder.accept(holder);
        }
    }

    public static Collection<LanguageHolder> getHolders() {
        return holders.values();
    }

    public static LanguageHolder get(Language language) {
        LanguageHolder holder = holders.get(language);
        return holder == null ? register(language) : holder;
    }
}
