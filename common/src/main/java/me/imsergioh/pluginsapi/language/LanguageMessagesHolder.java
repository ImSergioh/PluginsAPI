package me.imsergioh.pluginsapi.language;

import lombok.Getter;
import me.imsergioh.pluginsapi.instance.FilePluginConfig;

@Getter
public class LanguageMessagesHolder extends FilePluginConfig {

    // Milliseconds:
    public static final long CLEAR_MESSAGE_HOLDER_AFTER = 1000 * 60;

    private final String name;

    @Getter
    private long expirationMillis;

    public LanguageMessagesHolder(String name, LanguageHolder holder) {
        super(holder.getDir() + "/" + name + ".json");
        this.name = name;
        registerUsage();
        load();
    }

    public void registerUsage() {
        this.expirationMillis = System.currentTimeMillis() + CLEAR_MESSAGE_HOLDER_AFTER;
    }

}
