package me.imsergioh.pluginsapi.variables;

import me.imsergioh.pluginsapi.instance.IObjectVariableListener;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.entity.Player;

public class LinkVariable implements IObjectVariableListener<TextComponent, Player> {

    @Override
    public TextComponent parse(String message) {
        TextComponent component = Component.text(message);
        component = component.clickEvent(ClickEvent.clickEvent(ClickEvent.Action.OPEN_URL, "https://www.google.com"));
        return component;
    }

    @Override
    public TextComponent parse(Player player, String message) {
        return null;
    }
}
