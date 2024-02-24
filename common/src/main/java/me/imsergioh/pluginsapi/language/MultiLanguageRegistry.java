package me.imsergioh.pluginsapi.language;

import lombok.Getter;
import me.imsergioh.pluginsapi.handler.LanguagesHandler;
import me.imsergioh.pluginsapi.util.ChatUtil;
import me.imsergioh.pluginsapi.util.JavaUtil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

@Getter
public abstract class MultiLanguageRegistry implements IMultiLanguageRegistry {

    @Getter
    private static final Map<String, String> classesNames = new HashMap<>();

    @Getter
    private final String name;

    public MultiLanguageRegistry() {
        LangMessagesInfo info = getClass().getDeclaredAnnotation(LangMessagesInfo.class);
        this.name = info.name();
        classesNames.put(name, getClass().getName());
        LanguagesHandler.forEach(languageHolder -> {
            load(languageHolder.get(name));
        });
    }

    public String get(Language language, String path, Object... args) {
        return ChatUtil.parse(LanguagesHandler.get(language).get(name).getString(path), args);
    }

    public <Player> String get(Player player, Language language, String path, Object... args) {
        return ChatUtil.parse(player, LanguagesHandler.get(language).get(name).getString(path), args);
    }

    public static void reload(String name) {
        try {
            Class<? extends MultiLanguageRegistry> clazz = (Class<? extends MultiLanguageRegistry>) JavaUtil.findClass(classesNames.get(name));
            if (clazz == null) return;
            clazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
