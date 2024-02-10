package me.imsergioh.pluginsapi.language;

import lombok.Getter;
import me.imsergioh.pluginsapi.instance.FilePluginConfig;

@Getter
public class LanguageMessagesHolder extends FilePluginConfig {

    private final String name;

    public LanguageMessagesHolder(String name, LanguageHolder holder) {
        super(holder.getDir() + "/" + name + ".json");
        this.name = name;
        load();
    }

}
