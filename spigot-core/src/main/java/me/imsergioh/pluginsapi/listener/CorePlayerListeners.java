package me.imsergioh.pluginsapi.listener;

import org.bukkit.entity.Player;
import me.imsergioh.pluginsapi.instance.menu.CoreMenu;
import me.imsergioh.pluginsapi.instance.player.CorePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class CorePlayerListeners implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void removeCorePlayer(PlayerQuitEvent event) {
        CorePlayer.remove(event.getPlayer().getUniqueId());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void registerInventoryCloseInv(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player player)) return;
        CoreMenu menu = CorePlayer.get(player).getCurrentMenuOpen();
        if (menu == null) return;
        CorePlayer.get(event.getPlayer().getUniqueId()).registerMenuHistory(menu);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player player)) return;
        CorePlayer.get(player).setCurrentMenuOpen(null);
    }
}
