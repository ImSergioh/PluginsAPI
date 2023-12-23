package me.imsergioh.pluginsapi.manager;

import me.imsergioh.pluginsapi.SpigotPluginsAPI;
import me.imsergioh.pluginsapi.instance.SpigotYmlConfig;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import me.imsergioh.pluginsapi.instance.menu.ConfigurableMenu;
import me.imsergioh.pluginsapi.instance.menu.CoreMenu;

import java.io.File;
import java.util.Arrays;

public class MenusManager {

    private static final JavaPlugin plugin = SpigotPluginsAPI.getPlugin();

    public static ConfigurableMenu loadForPlayer(Player player, String name) {
        SpigotYmlConfig config = new SpigotYmlConfig(getMenuFile(name));
        registerDefaults(config);
        return new ConfigurableMenu(player, config);
    }

    public static <C extends CoreMenu> C loadForPlayer(Class<C> type, Player player, String name) {
        SpigotYmlConfig config = new SpigotYmlConfig(getMenuFile(name));
        registerDefaults(config);
        try {
            return type.getDeclaredConstructor(Player.class).newInstance(player);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void registerDefaults(SpigotYmlConfig config) {

        config.register("items.4.type", Material.BARRIER.name());
        config.register("items.4.name", "&cTest &7|&4 IN MAINTENANCE");
        config.register("items.4.lore", Arrays.asList("Usted es <name>", "Su rango es <rank>"));
        config.save();
    }

    private static File getMenuFile(String name) {
        return new File(getDirectory() + "/" + name + ".yml");
    }

    private static File getDirectory() {
        return new File(plugin.getDataFolder().getAbsolutePath() + "/menus");
    }
}
