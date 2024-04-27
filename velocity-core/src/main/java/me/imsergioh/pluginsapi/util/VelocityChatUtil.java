package me.imsergioh.pluginsapi.util;

import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VelocityChatUtil {

    private static final Pattern colorPattern = Pattern.compile("§[0-9a-fA-F]");

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
            char COLOR_CHAR = '§';
            matcher.appendReplacement(buffer, COLOR_CHAR + "x"
                    + COLOR_CHAR + group.charAt(0) + COLOR_CHAR + group.charAt(1)
                    + COLOR_CHAR + group.charAt(2) + COLOR_CHAR + group.charAt(3)
                    + COLOR_CHAR + group.charAt(4) + COLOR_CHAR + group.charAt(5)
            );
        }

        return matcher.appendTail(buffer).toString();
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
