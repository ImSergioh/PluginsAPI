package me.imsergioh.pluginsapi.instance.item;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.imsergioh.pluginsapi.listener.ItemActionListeners;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;
import me.imsergioh.pluginsapi.util.ChatUtil;

import java.lang.reflect.Field;
import java.util.*;

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

    public ItemBuilder amount(int amount) {
        item.setAmount(amount);
        return this;
    }

    public ItemBuilder data(int amount) {
        item.setDurability(((Number) amount).shortValue());
        return this;
    }

    public ItemBuilder skullTexture(String texture) {
        SkullMeta skullMeta = (SkullMeta) meta;
        GameProfile profile = new GameProfile(UUID.randomUUID(), "");
        profile.getProperties().put("textures", new Property("textures", texture));
        Field profileField;
        try {
            profileField = Objects.requireNonNull(skullMeta).getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(skullMeta, profile);
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }
        meta = skullMeta;
        item.setItemMeta(meta);
        return this;
    }

    public ItemStack get() {
        if (name != null)
            meta.setDisplayName(ChatUtil.parse(name, nameArgs));

        if (!lore.isEmpty()) {
            List<String> parsedLore = new ArrayList<>();
            lore.replaceAll(line -> ChatUtil.parse(line, loreArgs));
            for (String line : new ArrayList<>(parsedLore)) {
                parsedLore.addAll(Arrays.asList(line.split("\n")));
            }
            meta.setLore(parsedLore);
        }

        item.setItemMeta(meta);
        return item;
    }

    public ItemStack get(Player player) {
        if (name != null)
            meta.setDisplayName(ChatUtil.parse(player, name, nameArgs));

        if (!lore.isEmpty()) {
            List<String> parsedLore = new ArrayList<>();
            lore.replaceAll(line -> ChatUtil.parse(player, line, loreArgs));
            for (String line : lore) {
                parsedLore.addAll(Arrays.asList(line.split("\n")));
            }
            meta.setLore(parsedLore);
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

    public static String getID(ItemStack item) {
        StringBuilder builder = new StringBuilder(item.getType().name() + item.getAmount());

        try {
            builder.append(item.getItemMeta().getDisplayName());
        } catch (Exception ignore) {
        }

        try {
            builder.append(item.getItemMeta().getLore());
        } catch (Exception e) {
        }

        if (item.getItemMeta().getLore() != null) {
            builder.append(item.getItemMeta().getLore());
        }
        return builder.toString();
    }
}
