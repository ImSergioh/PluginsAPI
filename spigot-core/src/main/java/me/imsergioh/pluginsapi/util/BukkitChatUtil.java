package me.imsergioh.pluginsapi.util;

import me.imsergioh.pluginsapi.instance.PlayerLanguages;
import me.imsergioh.pluginsapi.language.IMessageCategory;
import me.imsergioh.pluginsapi.language.Language;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BukkitChatUtil {

    private static final Pattern legacyHexPattern = Pattern.compile("§[0-9a-fA-F]");

    public static void send(Player player, IMessageCategory category, Object... args) {
        Language language = PlayerLanguages.get(player.getUniqueId());
        String message = category.getObjectOfToString(language);
        player.sendMessage(parse(player, message, args).toLegacyText());
    }

    public static TextComponent parse(Player player, IMessageCategory category, Object... args) {
        Language language = PlayerLanguages.get(player.getUniqueId());
        String message = category.getObjectOfToString(language);
        return parse(player, message, args);
    }

    public static TextComponent parse(IMessageCategory category, Object... args) {
        Language language = Language.getDefault();
        String message = category.getObjectOfToString(language);
        return parse(message, args);
    }

    public static TextComponent parse(String message, Object... args) {
        message = translate(message);
        message = parseLegacyToMiniMessageHex(message);
        return new TextComponent(ChatUtil.parse(message, args));
    }

    public static TextComponent parse(Player player, String message, Object... args) {
        message = translate(message);
        message = parseLegacyToMiniMessageHex(message);
        return new TextComponent(ChatUtil.parse(player, message, args));
    }

    public static String translate(String text) {
        Pattern hexPattern = Pattern.compile("&#([A-Fa-f0-9]{6})");
        Matcher matcher = hexPattern.matcher(text);
        StringBuffer buffer = new StringBuffer(text.length() + 4 * 8);

        while (matcher.find()) {
            String group = matcher.group(1);
            matcher.appendReplacement(buffer, ChatColor.COLOR_CHAR + "x"
                    + ChatColor.COLOR_CHAR + group.charAt(0) + ChatColor.COLOR_CHAR + group.charAt(1)
                    + ChatColor.COLOR_CHAR + group.charAt(2) + ChatColor.COLOR_CHAR + group.charAt(3)
                    + ChatColor.COLOR_CHAR + group.charAt(4) + ChatColor.COLOR_CHAR + group.charAt(5)
            );
        }

        return ChatColor.translateAlternateColorCodes('&', matcher.appendTail(buffer).toString());
    }

    public static String parseLegacyToMiniMessageHex(String input) {
        // Convertir códigos de color §x a MiniMessage hex
        Matcher matcher = legacyHexPattern.matcher(input);
        StringBuffer output = new StringBuffer();
        String lastColor = null;

        while (matcher.find()) {
            String hexColor = matcher.group().substring(1); // Remove the § prefix
            if (hexColor.length() == 1) {
                hexColor = "0" + hexColor; // Add leading zero if needed
            }
            lastColor = hexColor;
            matcher.appendReplacement(output, "");
        }
        matcher.appendTail(output);

        if (lastColor != null) {
            // Si hay un color al final del texto, añadirlo al final de la cadena
            output.insert(0, "<color:#" + lastColor + ">");
        }
        return output.toString();
    }
}