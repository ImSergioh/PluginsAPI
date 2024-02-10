package me.imsergioh.pluginsapi.message;

import me.imsergioh.pluginsapi.instance.PlayerLanguages;
import me.imsergioh.pluginsapi.language.*;
import org.bukkit.entity.Player;

@LangMessagesInfo(name = "language")
public class LanguageMessages extends MultiLanguageRegistry {

    private static final LanguageMessages languageMessages = new LanguageMessages();

    public String getSetLangTitle(Player player) {
        Language language = PlayerLanguages.get(player.getUniqueId());
        return get(player, language, "set_lang_title");
    }

    public static LanguageMessages get() {
        return languageMessages;
    }

    @Override
    public void load(LanguageMessagesHolder holder) {
        holder.registerDefault("set_lang_title", "Change language");
        holder.remove("selected");

        holder.registerDefault("menu_previous", "Previous");
        holder.registerDefault("menu_close", "Close");

        for (Language language : Language.values()) {
            holder.registerDefault("lang_" + language.name() + "_name", "LANG:" + language.name());
            holder.registerDefault("lang_" + language.name() + "_name", "LANG:" + language.name());
        }
        holder.save();
    }
}
