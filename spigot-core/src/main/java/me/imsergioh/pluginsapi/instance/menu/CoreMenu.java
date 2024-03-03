package me.imsergioh.pluginsapi.instance.menu;

import lombok.Getter;
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

    @Getter
    protected final Player initPlayer;
    protected CorePlayer initCorePlayer;
    protected final int size;
    protected final Inventory inventory;

    @Getter
    protected final MenuItemActionManager actionManager = new MenuItemActionManager();

    protected CoreMenu(Player player, int size, String title) {
        this.initPlayer = player;
        if (initPlayer != null)
            this.initCorePlayer = CorePlayer.get(player);
        this.size = size;
        if (title == null) {
            this.inventory = Bukkit.createInventory(null, size);
        } else {
            String formattedTitle = player == null ? ChatUtil.parse(title) : ChatUtil.parse(player, title);
            this.inventory = Bukkit.createInventory(null, size, formattedTitle);
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
        CorePlayer corePlayer = CorePlayer.get(player);
        corePlayer.setCurrentMenuOpen(this);
    }

    @Override
    public void set(Player player) {
        load();
        player.getInventory().setContents(inventory.getContents());
        player.updateInventory();
        CorePlayer corePlayer = CorePlayer.get(player);
        corePlayer.setCurrentMenuSet(this);
    }

    public ItemStack getPreviousMenuItem(Player player) {
        return ItemBuilder.of(Material.BOOK)
                .name("&c<lang.language.menu_previous>").get(player);
    }

    public ItemStack getCloseMenuItem(Player player) {
        return ItemBuilder.of(Material.BARRIER)
                .name("&c<lang.language.menu_close>").get(player);
    }
}
