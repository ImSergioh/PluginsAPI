package me.imsergioh.pluginsapi.event;

import me.imsergioh.pluginsapi.connection.RedisConnection;
import me.imsergioh.pluginsapi.language.Language;
import me.imsergioh.pluginsapi.instance.player.CorePlayer;

public class PlayerLanguageChangedEvent extends CorePlayerEvent {

    private final Language previousLanguage;
    private final Language newLanguage;

    public PlayerLanguageChangedEvent(CorePlayer corePlayer,
                                      Language previousLanguage, Language newLanguage) {
        super(corePlayer);
        this.previousLanguage = previousLanguage;
        this.newLanguage = newLanguage;
        RedisConnection.mainConnection.getResource().publish("lang:change",
                corePlayer.get().getUniqueId().toString() + " " + newLanguage.name());
    }

    public Language getPreviousLanguage() {
        return previousLanguage;
    }

    public Language getNewLanguage() {
        return newLanguage;
    }
}
