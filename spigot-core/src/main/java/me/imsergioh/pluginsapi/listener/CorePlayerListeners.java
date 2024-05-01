package me.imsergioh.pluginsapi.listener;

import me.imsergioh.pluginsapi.language.TestMessages;
import me.imsergioh.pluginsapi.util.PaperChatUtil;
import org.bukkit.entity.Player;
import me.imsergioh.pluginsapi.instance.menu.CoreMenu;
import me.imsergioh.pluginsapi.instance.player.CorePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class CorePlayerListeners implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void removeCorePlayer(PlayerQuitEvent event) {
        CorePlayer.remove(event.getPlayer().getUniqueId());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void registerInventoryCloseInv(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player)) return;
        Player player = (Player) event.getPlayer();
        CorePlayer corePlayer = CorePlayer.get(player);
        if (corePlayer == null) return;
        CoreMenu menu = corePlayer.getCurrentMenuOpen();
        if (menu == null) return;
        CorePlayer.get(event.getPlayer().getUniqueId()).registerMenuHistory(menu);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player)) return;
        Player player = (Player) event.getPlayer();
        CorePlayer corePlayer = CorePlayer.get(player);
        if (corePlayer == null) return;
        corePlayer.setCurrentMenuOpen(null);

        // TODO: REMOVE THIS
        PaperChatUtil.send(player, TestMessages.testMessageList);
    }
}
