package me.imsergioh.pluginsapi.instance.item;

import me.imsergioh.pluginsapi.instance.PlayerLanguages;
import me.imsergioh.pluginsapi.language.Language;
import me.imsergioh.pluginsapi.listener.ItemActionListeners;
import me.imsergioh.pluginsapi.util.ChatUtil;
import me.imsergioh.pluginsapi.util.LanguageUtil;
import me.imsergioh.pluginsapi.util.PaperChatUtil;
import me.imsergioh.pluginsapi.util.SkullCreator;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

public class ItemBuilder {

    private final ItemStack item;
    private ItemMeta meta;

    private String name;
    private Object[] nameArgs;

    private List<String> lore = new ArrayList<>();
    private Object[] loreArgs;

    protected ItemBuilder(Material material) {
        item = new ItemStack(material, 1);
        meta = item.getItemMeta();
    }

    public ItemBuilder name(String name, Object... args) {
        this.name = name;
        this.nameArgs = args;
        return this;
    }

    public ItemBuilder lore(List<String> lore, Object... args) {
        this.lore = lore;
        this.loreArgs = args;
        return this;
    }

    public ItemBuilder lore(String... lores) {
        this.lore = Arrays.asList(lores);
        return this;
    }

    public ItemBuilder amount(int amount) {
        item.setAmount(amount);
        return this;
    }

    public ItemBuilder data(int amount) {
        item.setDurability(((Number) amount).shortValue());
        return this;
    }

    private static String urlToBase64(String url) {
        URI actualUrl;
        try {
            actualUrl = new URI(url);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        String toEncode = "{\"textures\":{\"SKIN\":{\"url\":\"" + actualUrl + "\"}}}";
        return Base64.getEncoder().encodeToString(toEncode.getBytes());
    }

    public ItemBuilder skullTexture(String texture) {
        ItemStack parsedItem = SkullCreator.itemWithBase64(item, texture);
        assert parsedItem != null;
        meta = parsedItem.getItemMeta();
        return this;
    }

    public ItemBuilder hideFlags() {
        for (ItemFlag flag : ItemFlag.values()) {
            meta.addItemFlags(flag);
        }
        return this;
    }

    public ItemStack get(Language language) {
        if (name != null)
            meta.displayName(PaperChatUtil.parse(LanguageUtil.parse(language, name), nameArgs));
        if (lore != null && !lore.isEmpty()) {
            lore.replaceAll(line -> ChatUtil.parse(LanguageUtil.parse(language, line)));
            meta.lore(modernLore(lore, null));
        }
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack get() {
        if (name != null)
            meta.displayName(PaperChatUtil.parse(name, nameArgs));

        if (!lore.isEmpty()) {
            lore.replaceAll(ChatUtil::parse);
            meta.lore(modernLore(lore, null));
        }
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack get(Player player) {
        if (name != null)
            meta.displayName(PaperChatUtil.parse(player, name, nameArgs));

        if (!lore.isEmpty()) {
            lore.replaceAll(line -> ChatUtil.parse(LanguageUtil.parse(PlayerLanguages.get(player.getUniqueId()), line)));
            meta.lore(modernLore(lore, player));
        }

        item.setItemMeta(meta);
        return item;
    }

    public ItemMeta meta() {
        return meta;
    }

    public static ItemBuilder of(Material material) {
        return new ItemBuilder(material);
    }

    public static void setup(JavaPlugin plugin) {
        Bukkit.getPluginManager().registerEvents(new ItemActionListeners(), plugin);
    }

    public List<Component> modernLore(List<String> initialLore, Player player) {
        List<Component> list = new ArrayList<>();
        for (String line : initialLore) {
            if (line.contains("\n")) {
                String[] lines = line.split("\n");
                for (String subLine : lines) {
                    Component formatted = player == null ? PaperChatUtil.parse(subLine, loreArgs) : PaperChatUtil.parse(player, subLine, loreArgs);
                    list.add(formatted);
                }
            } else {
                Component formatted = player == null ? PaperChatUtil.parse(line, loreArgs) : PaperChatUtil.parse(player, line, loreArgs);
                list.add(formatted);
            }
        }
        return list;
    }
}
