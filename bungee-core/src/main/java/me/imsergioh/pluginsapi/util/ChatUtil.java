package me.imsergioh.pluginsapi.util;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.HashMap;
import java.util.Map;


public class ChatUtil {

    private static final Map<String, String> miniMessageAdapter = new HashMap<>();

    static {
        miniMessageAdapter.put("<black>", "§0");
        miniMessageAdapter.put("<dark_blue>", "§1");
        miniMessageAdapter.put("<dark_green>", "§2");
        miniMessageAdapter.put("<dark_aqua>", "§3");
        miniMessageAdapter.put("<dark_red>", "§4");
        miniMessageAdapter.put("<dark_purple>", "§5");
        miniMessageAdapter.put("<gold>", "§6");
        miniMessageAdapter.put("<gray>", "§7");
        miniMessageAdapter.put("<dark_gray>", "§8");
        miniMessageAdapter.put("<blue>", "§9");
        miniMessageAdapter.put("<green>", "§a");
        miniMessageAdapter.put("<aqua>", "§b");
        miniMessageAdapter.put("<red>", "§c");
        miniMessageAdapter.put("<light_purple>", "§d");
        miniMessageAdapter.put("<yellow>", "§e");
        miniMessageAdapter.put("<white>", "§f");

        miniMessageAdapter.put("<obfuscated>", "§k");
        miniMessageAdapter.put("<bold>", "§l");
        miniMessageAdapter.put("<strikethrough>", "§m");
        miniMessageAdapter.put("<underline>", "§n");
        miniMessageAdapter.put("<italic>", "§o");
        miniMessageAdapter.put("<reset>", "§r");
    }

    public static TextComponent parse(String message, Object... args) {
        message = LegacyChatUtil.parse(message, args);
        message = parseMiniMessage(message);
        return new TextComponent(message);
    }

    public static TextComponent parse(ProxiedPlayer player, String message, Object... args) {
        message = LegacyChatUtil.parse(player, message, args);
        message = parseMiniMessage(message);
        return new TextComponent(message);
    }

    public static String parseMiniMessage(String message) {
        for (String key : miniMessageAdapter.keySet()) {
            if (message.contains(key)) message = message.replace(key, miniMessageAdapter.get(key));
        }
        return message;
    }
}
