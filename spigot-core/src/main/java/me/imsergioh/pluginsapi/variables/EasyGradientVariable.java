package me.imsergioh.pluginsapi.variables;

import me.imsergioh.pluginsapi.instance.VariableListener;
import me.imsergioh.pluginsapi.util.GradientUtil;
import org.bukkit.entity.Player;

public class EasyGradientVariable extends VariableListener<Player> {

    @Override
    public String parse(String message) {
        while (message.contains("<egradient:#")) {
            int start = message.indexOf("<egradient:#");
            int end = message.indexOf(">", start);
            if (end == -1) break;
            String colorCode = message.substring(start + 10, end).replace(":", "");

            int lightenFactor = colorCode.contains("@") ? 0 : 1;
            colorCode = colorCode.replace("@", "");

            String lightColor = GradientUtil.lightenColor(colorCode, lightenFactor);

            String gradientTag = "<gradient:" + colorCode + ":" + lightColor + ">";
            message = message.substring(0, start) + gradientTag + message.substring(end + 1);
        }
        return message;
    }

    @Override
    public String parse(Player player, String message) {
        return parse(message);
    }
}
