package me.imsergioh.pluginsapi.listener;

import org.bukkit.entity.Player;
import me.imsergioh.pluginsapi.instance.menu.CoreMenu;
import me.imsergioh.pluginsapi.instance.player.CorePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class CorePlayerListeners implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void removeCorePlayer(PlayerQuitEvent event) {
        CorePlayer.remove(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void registerInventoryCloseInv(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player)) return;
        Player player = (Player) event.getPlayer();
        CoreMenu menu = CorePlayer.get(player).getCurrentMenuOpen();
        if (menu == null) return;
        CorePlayer.get(event.getPlayer().getUniqueId()).registerMenuHistory(menu);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player)) return;
        Player player = (Player) event.getPlayer();
        CorePlayer.get(player).setCurrentMenuOpen(null);
    }
}
