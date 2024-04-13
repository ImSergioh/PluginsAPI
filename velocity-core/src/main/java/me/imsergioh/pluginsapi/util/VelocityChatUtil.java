package me.imsergioh.pluginsapi.util;

import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class VelocityChatUtil {

    public static Component parse(String message, Object... args) {
        message = ChatUtil.parse(message, args);
        MiniMessage miniMessage = MiniMessage.miniMessage();
        return miniMessage.deserialize(message);

    }

    public static Component parse(Player player, String message, Object... args) {
        message = ChatUtil.parse(player, message, args);
        MiniMessage miniMessage = MiniMessage.miniMessage();
        return miniMessage.deserialize(message);
    }
}
