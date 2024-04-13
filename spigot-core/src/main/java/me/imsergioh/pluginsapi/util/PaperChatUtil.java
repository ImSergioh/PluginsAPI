package me.imsergioh.pluginsapi.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PaperChatUtil {

    private static final Pattern colorPattern = Pattern.compile("§[0-9a-fA-F]");

    public static Component parse(String message, Object... args) {
        message = ChatUtil.parse(message, args);
        MiniMessage miniMessage = MiniMessage.miniMessage();
        message = parseLegacyToMiniMessageHex(message);
        return miniMessage.deserialize(message)
                .decoration(TextDecoration.ITALIC, false);
    }

    public static Component parse(Player player, String message, Object... args) {
        message = ChatUtil.parse(player, message, args);
        MiniMessage miniMessage = MiniMessage.miniMessage();
        message = parseLegacyToMiniMessageHex(message);
        return miniMessage.deserialize(message)
                .decoration(TextDecoration.ITALIC, false);
    }

    public static String parseLegacyToMiniMessageHex(String input) {
        // Convertir códigos de color §x a MiniMessage hex
        Matcher matcher = colorPattern.matcher(input);
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
            output.insert(0, "<#" + lastColor + ">");
        }
        return output.toString();
    }
}