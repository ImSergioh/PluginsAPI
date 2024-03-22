package me.imsergioh.pluginsapi.util;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.inventory.meta.BookMeta;

public class BukkitUtil {

    public static void addComponentPageToBook(BookMeta meta, BaseComponent... components) {
        meta.spigot().addPage(components);
    }

    public static void addComponentToBook(BookMeta meta, BaseComponent... components) {
        int currentPageIndex = meta.getPages().size() - 1;
        BaseComponent component = meta.spigot().getPages().get(currentPageIndex)[0];
        BaseComponent newPage = new TextComponent(ChatUtil.parse(ComponentSerializer.toString(components)));
        component.addExtra(newPage);
        meta.spigot().setPage(currentPageIndex, component);
    }
}
