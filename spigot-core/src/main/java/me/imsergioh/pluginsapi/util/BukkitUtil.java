package me.imsergioh.pluginsapi.util;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftMetaBook;
import org.bukkit.inventory.meta.BookMeta;

public class BukkitUtil {

    public static void addComponentPageToBook(BookMeta meta, BaseComponent... components) {
        CraftMetaBook craftMetaBook = (CraftMetaBook) meta;
        IChatBaseComponent page = IChatBaseComponent.ChatSerializer.a(ComponentSerializer.toString(components));
        craftMetaBook.pages.add(page);
    }

    public static void addComponentToBook(BookMeta meta, BaseComponent... components) {
        CraftMetaBook craftMetaBook = (CraftMetaBook) meta;
        int currentPageIndex = craftMetaBook.pages.size() - 1;
        IChatBaseComponent component = craftMetaBook.pages.get(currentPageIndex);
        IChatBaseComponent newPage = IChatBaseComponent.ChatSerializer.a(ChatUtil.parse(ComponentSerializer.toString(components)));
        component.a(ChatUtil.parse(component.getText()));
        component.addSibling(newPage);
        craftMetaBook.pages.set(currentPageIndex, component);
    }

    public static CraftMetaBook getBookMeta(BookMeta meta) {
        return (CraftMetaBook) meta;
    }

}
