package menu;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import instance.SpigotAPIConfig;
import item.ItemBuilder;

import java.util.List;

public class ConfigurableMenu extends CoreMenu {

    protected final SpigotAPIConfig config;

    public ConfigurableMenu(Player player, SpigotAPIConfig config) {
        super(player, getSize(config), getTitle(config));
        this.config = config;
        loadItemsFromSpecificPath(player, "items");
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

    private static int getSize(SpigotAPIConfig config) {
        config.register("size", 27);
        return config.getInt("size");
    }

    private static String getTitle(SpigotAPIConfig config) {
        config.register("title", "Default menu title");
        return config.getString("title");
    }

}
