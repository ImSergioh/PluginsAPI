package me.imsergioh.pluginsapi.util;

import me.imsergioh.pluginsapi.handler.VariablesHandler;

import java.text.MessageFormat;

public class LegacyChatUtil {

    public static <Player> String parse(Player player, String message) {
        if (player != null) {
            message = VariablesHandler.parse(player, message);
        }
        return parse(message);
    }

    public static <Player> String parse(Player player, String message, Object... args) {
        message = parse(player, message);
        return MessageFormat.format(message, args);
    }

    public static String parse(String message, Object... args) {
        message = parse(message);
        return MessageFormat.format(message, args);
    }

    public static String parse(String message) {
        message = VariablesHandler.parse(message);
        return message.replace("&" ,"§");
    }

}
