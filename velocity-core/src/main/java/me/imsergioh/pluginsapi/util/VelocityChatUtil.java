package me.imsergioh.pluginsapi.util;

import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class VelocityChatUtil {

    public static String parse(String message, Object... args) {
        return parseToComponent(message, args).insertion();
    }

    public static String parse(Player player, String message, Object... args) {
        return parseToComponent(player, message, args).insertion();
    }

    public static Component parseToComponent(String message, Object... args) {
        message = ChatUtil.parse(message, args);
        Component legacyComponent = LegacyComponentSerializer.legacySection().deserialize(message);
        MiniMessage miniMessage = MiniMessage.miniMessage();
        String miniMessageText = miniMessage.serialize(legacyComponent);
        return miniMessage.deserialize(miniMessageText);
    }

    public static Component parseToComponent(Player player, String message, Object... args) {
        message = ChatUtil.parse(player, message, args);
        Component legacyComponent = LegacyComponentSerializer.legacySection().deserialize(message);
        MiniMessage miniMessage = MiniMessage.miniMessage();
        String miniMessageText = miniMessage.serialize(legacyComponent);
        return miniMessage.deserialize(miniMessageText);
    }
}
