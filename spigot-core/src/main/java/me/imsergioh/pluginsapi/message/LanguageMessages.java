package me.imsergioh.pluginsapi.message;

import me.imsergioh.pluginsapi.instance.PlayerLanguages;
import me.imsergioh.pluginsapi.language.*;
import org.bukkit.entity.Player;

@LangMessagesInfo(name = "language")
public class LanguageMessages extends MultiLanguageRegistry {

    private static final LanguageMessages languageMessages = new LanguageMessages();

    public String getSetLangTitle(Player player) {
        Language language = PlayerLanguages.get(player.getUniqueId());
        return get(player, language, "setLangTitle");
    }

    public static LanguageMessages get() {
        return languageMessages;
    }

    @Override
    public void load(LanguageMessagesHolder holder) {
        holder.registerDefault("setLangTitle", "Change language");
        holder.remove("selected");

        holder.registerDefault("menuPrevious", "Previous");
        holder.registerDefault("menuClose", "Close");

        for (Language language : Language.values()) {
            holder.registerDefault("lang." + language.name() + ".name", "LANG:" + language.name());
            holder.registerDefault("lang." + language.name() + ".name", "LANG:" + language.name());
        }
        holder.save();
    }
}
