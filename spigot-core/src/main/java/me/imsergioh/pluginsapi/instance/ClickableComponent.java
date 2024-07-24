package me.imsergioh.pluginsapi.instance;

import lombok.Getter;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import me.imsergioh.pluginsapi.util.ChatUtil;

@Getter
public class ClickableComponent {

    private final TextComponent builder = new TextComponent("");

    public ClickableComponent() {
    }

    public void addURL(String text, String url) {
        builder.addExtra(text);
        builder.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url));
    }

    public void addCommandSuggestion(String text, String command) {
        builder.addExtra(text);
        builder.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, command));
    }

    public void addRunCommand(String text, String command) {
        builder.addExtra(text);
        builder.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command));
    }

    public void addText(String text) {
        builder.addExtra(ChatUtil.parse(text));
    }

    public void send(Player player) {
        player.sendMessage(builder.toLegacyText());
    }
}
