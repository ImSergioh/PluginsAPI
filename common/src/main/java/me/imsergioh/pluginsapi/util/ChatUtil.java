package me.imsergioh.pluginsapi.util;

import me.imsergioh.pluginsapi.handler.VariablesHandler;

import java.text.MessageFormat;
import java.util.HashMap;

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
        if (message != null) {
            message = VariablesHandler.parse(message);
            return color(message);
        }
        return null;
    }

    public static String color(String oldMessage) {
        StringBuilder miniMessage = new StringBuilder();

        boolean underline = false; // Variable para controlar si estamos dentro de una sección subrayada
        boolean obfuscated = false; // Variable para controlar si estamos dentro de una sección obfuscated

        int index = 0;
        while (index < oldMessage.length()) {
            char currentChar = oldMessage.charAt(index);
            if (currentChar == '&') {
                if (index + 1 < oldMessage.length()) {
                    String code = String.valueOf(currentChar) + oldMessage.charAt(index + 1);
                    if (modernColorMap.containsKey(code)) {
                        if (code.equals("&n")) {
                            // Si encontramos el código para subrayado, cambiamos el estado de la variable
                            underline = !underline;
                            if (underline) {
                                miniMessage.append("<u>");
                            } else {
                                miniMessage.append("</u>");
                            }
                        } else if (code.equals("&k")) {
                            // Si encontramos el código para obfuscated, cambiamos el estado de la variable
                            obfuscated = !obfuscated;
                            if (obfuscated) {
                                miniMessage.append("<obfuscated>");
                            } else {
                                miniMessage.append("</obfuscated>");
                            }
                        } else {
                            // Si es un código de color, lo aplicamos normalmente
                            miniMessage.append("<").append(modernColorMap.get(code)).append(">");
                        }
                        index++;
                    }
                }
            } else {
                miniMessage.append(currentChar);
            }
            index++;
        }

        // Si terminamos dentro de una sección subrayada, la cerramos
        if (underline) {
            miniMessage.append("</u>");
        }

        // Si terminamos dentro de una sección obfuscated, la cerramos
        if (obfuscated) {
            miniMessage.append("</obfuscated>");
        }
        return miniMessage.toString();
    }


}
