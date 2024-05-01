package me.imsergioh.pluginsapi.util;

import me.imsergioh.pluginsapi.instance.PlayerLanguages;
import me.imsergioh.pluginsapi.language.IMessageCategory;
import me.imsergioh.pluginsapi.language.Language;
import me.imsergioh.pluginsapi.language.TestMessages;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PaperChatUtil {

    private static final Pattern legacyHexPattern = Pattern.compile("§[0-9a-fA-F]");

    public static void send(Player player, IMessageCategory category, Object... args) {
        Language language = PlayerLanguages.get(player.getUniqueId());
        String message = category.getMessageOf(language);
        player.sendMessage(parse(player, message, args));
    }

    public static Component parse(String message, Object... args) {
        message = ChatUtil.parse(message, args);
        MiniMessage miniMessage = MiniMessage.miniMessage();
        message = translate(message);
        message = parseLegacyToMiniMessageHex(message);
        return miniMessage.deserialize(message)
                .decoration(TextDecoration.ITALIC, false);
    }

    public static Component parse(Player player, String message, Object... args) {
        message = ChatUtil.parse(player, message, args);
        MiniMessage miniMessage = MiniMessage.miniMessage();
        message = translate(message);
        message = parseLegacyToMiniMessageHex(message);
        return miniMessage.deserialize(message)
                .decoration(TextDecoration.ITALIC, false);
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