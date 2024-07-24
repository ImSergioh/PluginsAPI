package me.imsergioh.pluginsapi.util;

import me.imsergioh.pluginsapi.handler.VariablesHandler;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class ChatUtil {

    private static final HashMap<String, String> modernColorMap = new HashMap<>();

    static {
        modernColorMap.put("&0", "black");
        modernColorMap.put("&1", "dark_blue");
        modernColorMap.put("&2", "dark_green");
        modernColorMap.put("&3", "dark_aqua");
        modernColorMap.put("&4", "dark_red");
        modernColorMap.put("&5", "dark_purple");
        modernColorMap.put("&6", "gold");
        modernColorMap.put("&7", "gray");
        modernColorMap.put("&8", "dark_gray");
        modernColorMap.put("&9", "blue");
        modernColorMap.put("&a", "green");
        modernColorMap.put("&b", "aqua");
        modernColorMap.put("&c", "red");
        modernColorMap.put("&d", "light_purple");
        modernColorMap.put("&e", "yellow");
        modernColorMap.put("&f", "white");
        modernColorMap.put("&g", "#DDD605");
        modernColorMap.put("&h", "#E3D4D1");
        modernColorMap.put("&i", "#CECACA");
        modernColorMap.put("&j", "#443A3B");

        modernColorMap.put("&k", "obfuscated");
        modernColorMap.put("&l", "bold");
        modernColorMap.put("&m", "strikethrough");
        modernColorMap.put("&n", "underline");
        modernColorMap.put("&o", "italic");
        modernColorMap.put("&r", "reset");
    }

    public static <Player> String parse(Player player, String message) {
        if (player != null && message != null) {
            message = VariablesHandler.parse(player, message);
            message = color(message);
        }
        return parse(message);
    }

    public static <Player> String parse(Player player, String message, Object... args) {
        message = parse(player, message);
        message = color(message);
        try {
            return MessageFormat.format(message, args);
        } catch (Exception e) {
            return color(message);
        }
    }

    public static String parse(String message, Object... args) {
        message = parse(message);
        message = color(message);
        return color(MessageFormat.format(message, args));
    }

    public static String parse(String message) {
        if (message != null) {
            message = VariablesHandler.parse(message);
            return color(message);
        }
        return null;
    }

    public static String color(String msg) {
        return msg.replace("&", "§");
    }


}
