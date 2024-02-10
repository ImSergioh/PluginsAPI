package me.imsergioh.pluginsapi.message;

import me.imsergioh.pluginsapi.language.Language;
import me.imsergioh.pluginsapi.language.LanguageMessagesHolder;
import me.imsergioh.pluginsapi.language.MultiLanguageRegistry;

public abstract class BukkitLangMessages extends MultiLanguageRegistry {

    protected LanguageMessagesHolder registerTitle(LanguageMessagesHolder holder, String name, String title, String subtitle) {
        String titlePath = "title." + name + ".title";
        String subtitlePath = "title." + name + ".subtitle";
        holder.registerDefault(titlePath, title);
        holder.registerDefault(subtitlePath, subtitle);
        return holder;
    }

    public String getChatMessage(Language language, String name) {
        return get(language, "chat_message." + name);
    }

    public String[] getTitle(Language language, String name) {
        String title = get(language, "title." + name + ".title");
        String subtitle = get(language, "title." + name + ".subtitle");
        return new String[]{title, subtitle};
    }
}
