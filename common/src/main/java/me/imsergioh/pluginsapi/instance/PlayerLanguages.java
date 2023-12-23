package me.imsergioh.pluginsapi.instance;

import me.imsergioh.pluginsapi.language.Language;

import java.util.HashMap;
import java.util.UUID;

public class PlayerLanguages {

    private static final HashMap<UUID, Language> languages = new HashMap<>();

    public static Language get(UUID id) {
        return languages.get(id);
    }

    public static void register(UUID id, Language language) {
        languages.put(id, language);
    }

    public static void unregister(UUID id) {
        languages.remove(id);
    }

}
