package me.imsergioh.pluginsapi.util;

import me.imsergioh.pluginsapi.connection.RedisConnection;
import me.imsergioh.pluginsapi.instance.backend.request.ServerRedirectionRequest;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PluginUtils {

    public static void redirectTo(Player player, String serverPrefix) {
        try {
            System.out.println("Redirecting " + player.getName() + " to " + serverPrefix);
            ServerRedirectionRequest request = new ServerRedirectionRequest(player.getUniqueId(), player.getName(), serverPrefix);
            request.send(RedisConnection.mainConnection);
        } catch (Exception e) {
            player.sendMessage(ChatColor.RED + "Error when trying to connect to a server of '" + serverPrefix + "'");
            throw new RuntimeException(e);
        }
    }
}
