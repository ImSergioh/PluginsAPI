package me.imsergioh.pluginsapi.instance.menu;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import me.imsergioh.pluginsapi.instance.SpigotYmlConfig;
import me.imsergioh.pluginsapi.instance.item.ItemBuilder;

import java.util.List;

public class ConfigurableMenu extends CoreMenu {

    protected final SpigotYmlConfig config;

    public ConfigurableMenu(Player player, SpigotYmlConfig config, int size, String title) {
        super(player, size, title);
        this.config = config;
        loadItemsFromSpecificPath(player, "items");
    }

    public ConfigurableMenu(Player player, SpigotYmlConfig config) {
        this(player, config, getSize(config), getTitle(config));
    }

    protected void loadItemsFromSpecificPath(Player player, String path) {
        ConfigurationSection section = config.getSection(path);
        if (section == null) return;
        for (String sectionPath : section.getKeys(false)) {
            try {
                int slot = Integer.parseInt(sectionPath);
                String itemPath = section.getName() + "." + sectionPath;
                ItemBuilder builder = config.getItem(player, itemPath);
                ItemStack item = builder.get(player);
                if (config.contains(itemPath + ".actions")) {
                    List<String> actions = config.getStringList(itemPath + ".actions");
                    actionManager.registerItemAction(slot, item, actions);
                }
                set(slot, item);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error trying to parse slot to ConfigurableMenu (" + sectionPath + ")");
            }
        }
    }

    @Override
    public void load() {

    }

    private static int getSize(SpigotYmlConfig config) {
        config.register("size", 27);
        return config.getInt("size");
    }

    private static String getTitle(SpigotYmlConfig config) {
        config.register("title", "Default menu title");
        return config.getString("title");
    }

}
