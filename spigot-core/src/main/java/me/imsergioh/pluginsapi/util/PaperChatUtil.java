package me.imsergioh.pluginsapi.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;

import java.text.MessageFormat;

public class PaperChatUtil {

    public static String color(String message, Object... args) {
        String formattedMessageWithArgs = MessageFormat.format(message, args);
        return ChatUtil.color(formattedMessageWithArgs);
    }

    public static String parse(String message, Object... args) {
        return parseToComponent(message, args).insertion();
    }

    public static String parse(Player player, String message, Object... args) {
        return parseToComponent(player, message, args).insertion();
    }

    public static Component parseToComponent(String message, Object... args) {
        message = ChatUtil.parse(message, args);
        MiniMessage miniMessage = MiniMessage.miniMessage();
        return miniMessage.deserialize(message);
    }

    public static Component parseToComponent(Player player, String message, Object... args) {
        message = ChatUtil.parse(player, message, args);
        MiniMessage miniMessage = MiniMessage.miniMessage();
        return miniMessage.deserialize(message);
    }

}
