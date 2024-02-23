package me.imsergioh.pluginsapi.instance.menu;

import me.imsergioh.pluginsapi.instance.player.CorePlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import me.imsergioh.pluginsapi.manager.MenuItemActionManager;
import me.imsergioh.pluginsapi.instance.item.ItemBuilder;
import me.imsergioh.pluginsapi.util.ChatUtil;

import java.util.Arrays;

public abstract class CoreMenu implements ICoreMenu {

    protected final Player player;
    protected final CorePlayer corePlayer;
    protected final int size;
    protected final Inventory inventory;

    protected final MenuItemActionManager actionManager = new MenuItemActionManager();

    protected CoreMenu(Player player, int size, String title) {
        this.player = player;
        this.corePlayer = CorePlayer.get(player);
        this.size = size;
        if (title == null) {
            this.inventory = Bukkit.createInventory(null, size);
        } else {
            this.inventory = Bukkit.createInventory(null, size, ChatUtil.parse(player, title));
        }
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public ItemStack get(int slot) {
        return inventory.getItem(slot);
    }

    @Override
    public void set(int slot, ItemStack item) {
        inventory.setItem(slot, item);
    }

    @Override
    public void set(int slot, ItemStack item, String... labelCommands) {
        inventory.setItem(slot, item);
        actionManager.registerItemAction(slot, item, Arrays.asList(labelCommands));
    }

    @Override
    public void open(Player player) {
        load();
        player.openInventory(inventory);
        CorePlayer.get(player).setCurrentMenuOpen(this);
        player.playSound(player.getLocation(), Sound.CLICK, 0.1F, 2.5F);
    }

    @Override
    public void set(Player player) {
        load();
        CorePlayer.get(player).setCurrentMenuSet(this);
        player.getInventory().setContents(inventory.getContents());
        player.updateInventory();
    }

    public ItemStack getPreviousMenuItem(Player player) {
        ItemStack item = ItemBuilder.of(Material.BOOK)
                .name("&c<lang.language.menu_previous>").get(player);
        return item;
    }

    public ItemStack getCloseMenuItem(Player player) {
        return ItemBuilder.of(Material.BARRIER)
                .name("&c<lang.language.menu_close>").get(player);
    }

    public MenuItemActionManager getActionManager() {
        return actionManager;
    }
}
