package me.imsergioh.pluginsapi.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class SpigotChatUtil {

    public Component parse(String message) {
        MiniMessage miniMessage = MiniMessage.miniMessage();
        return miniMessage.deserialize(message);
    }
}
