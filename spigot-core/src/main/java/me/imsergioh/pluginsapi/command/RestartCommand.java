package me.imsergioh.pluginsapi.command;

import me.imsergioh.pluginsapi.connection.RedisConnection;
import me.imsergioh.pluginsapi.util.SyncUtil;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.ScheduledFuture;

public class RestartCommand extends BukkitCommand {

    public RestartCommand(String name) {
        super(name);
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!sender.hasPermission("*")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                //player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);
                return true;
            }
        }

        sender.sendMessage("Restarting server...");
        new Thread(() -> {
            RedisConnection.mainConnection.getResource().set("test", UUID.randomUUID().toString());
            RedisConnection.mainConnection.send("server:restart", Bukkit.getServer().getServerId() + "@" + Bukkit.getServer().getPort());
        }).start();

        SyncUtil.later(() -> {
            Bukkit.getServer().shutdown();
        }, 250);

        return false;
    }
}

