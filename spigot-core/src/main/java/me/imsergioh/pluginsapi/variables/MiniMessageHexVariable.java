package me.imsergioh.pluginsapi.variables;

import me.imsergioh.pluginsapi.instance.VariableListener;
import me.imsergioh.pluginsapi.util.GradientUtil;
import org.bukkit.entity.Player;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MiniMessageHexVariable extends VariableListener<Player> {

    private static final Pattern COLOR_PATTERN = Pattern.compile("<#([a-fA-F0-9]{6})>");

    @Override
    public String parse(String message) {
        Matcher matcher = COLOR_PATTERN.matcher(message);
        StringBuilder result = new StringBuilder();

        while (matcher.find()) {
            matcher.appendReplacement(result, "<color:#" + matcher.group(1) + ">");
        }
        matcher.appendTail(result);
        return result.toString();
    }

    @Override
    public String parse(Player player, String message) {
        return parse(message);
    }
}
