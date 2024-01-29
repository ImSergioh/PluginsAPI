package me.imsergioh.pluginsapi.language;

import lombok.Getter;
import me.imsergioh.pluginsapi.handler.LanguagesHandler;
import me.imsergioh.pluginsapi.util.ChatUtil;
import me.imsergioh.pluginsapi.util.JavaUtil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

@Getter
public class MultiLanguageRegistry {

    @Getter
    private static final Set<String> names = new HashSet<>();

    @Getter
    private final String name;

    public MultiLanguageRegistry(String name, Consumer<LanguageMessagesHolder> holder) {
        this.name = name;
        LanguagesHandler.forEach(languageHolder -> {
            holder.accept(languageHolder.register(name));
        });
        names.add(name);
    }

    public String get(Language language, String path, Object... args) {
        return ChatUtil.parse(LanguagesHandler.get(language).get(name).getString(path), args);
    }

    public <Player> String get(Player player, Language language, String path, Object... args) {
        return ChatUtil.parse(player, LanguagesHandler.get(language).get(name).getString(path), args);
    }

    public static void reload(String name) {
        try {
            Class<? extends MultiLanguageRegistry> clazz = (Class<? extends MultiLanguageRegistry>) JavaUtil.findClass(name);
            if (clazz == null) return;
            clazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
