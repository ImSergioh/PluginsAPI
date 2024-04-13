package me.imsergioh.pluginsapi.instance;

import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.entity.Player;
import me.imsergioh.pluginsapi.util.ChatUtil;

@Getter
public class ClickableComponent {

    private final TextComponent builder = Component.empty();

    public ClickableComponent() {
    }

    public void addURL(Component text, String url) {
        builder.append(text).clickEvent(net.kyori.adventure.text.event.ClickEvent.clickEvent(net.kyori.adventure.text.event.ClickEvent.Action.OPEN_URL, url));
    }

    public void addCommandSuggestion(Component text, String command) {
        builder.append(text).clickEvent(net.kyori.adventure.text.event.ClickEvent.clickEvent(net.kyori.adventure.text.event.ClickEvent.Action.SUGGEST_COMMAND, command));
    }

    public void addRunCommand(Component text, String command) {
        builder.append(text).clickEvent(net.kyori.adventure.text.event.ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, command));
    }

    public void addText(String text) {
        builder.append(Component.text(ChatUtil.parse(text)));
    }

    public void send(Player player) {
        player.sendMessage(builder);
    }
}
