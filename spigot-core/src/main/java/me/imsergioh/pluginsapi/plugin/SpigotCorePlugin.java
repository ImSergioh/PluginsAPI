package me.imsergioh.pluginsapi.plugin;

import lombok.Getter;
import me.imsergioh.pluginsapi.SpigotPluginsAPI;
import org.bukkit.plugin.java.JavaPlugin;

public class SpigotCorePlugin extends JavaPlugin {

    @Getter
    private static SpigotCorePlugin plugin;

    @Override
    public void onEnable() {
        plugin = this;
        getDataFolder().mkdirs();
        SpigotPluginsAPI.setup(this);
    }
}
