package me.imsergioh.pluginsapi;

import lombok.Getter;
import me.imsergioh.pluginsapi.instance.player.CorePlayer;
import me.imsergioh.pluginsapi.manager.ItemActionsManager;
import me.imsergioh.pluginsapi.command.LanguageCommand;
import me.imsergioh.pluginsapi.command.RestartCommand;
import me.imsergioh.pluginsapi.instance.item.ItemBuilder;
import me.imsergioh.pluginsapi.listener.CorePlayerListeners;
import me.imsergioh.pluginsapi.message.LanguageMessages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

public class SpigotPluginsAPI {

    @Getter
    private static JavaPlugin plugin;
    private static BukkitTask bukkitTask;

    public static void setup(JavaPlugin pl) {
        plugin = pl;
        ItemBuilder.setup(plugin);
        ItemActionsManager.registerDefaults();

        Bukkit.getPluginManager().registerEvents(new CorePlayerListeners(), pl);

        PluginCommand languageCommand = plugin.getCommand("language");
        if (languageCommand != null) {
            languageCommand.setExecutor(new LanguageCommand());
        } else {
            System.out.println("Command 'language' not found! (Not registered)");
        }
    }

    public static void startTickTask() {
        if (bukkitTask != null) return;
        bukkitTask = new BukkitRunnable() {
            @Override
            public void run() {
                Collection<CorePlayer> players = CorePlayer.getCorePlayers();
                if (!CorePlayer.isEnabledTickEvent() || players.isEmpty()) {
                    bukkitTask = null;
                    cancel();
                    return;
                }
                for (CorePlayer corePlayer : CorePlayer.getCorePlayers()) {
                    corePlayer.tickTask();
                }
            }
        }.runTaskTimerAsynchronously(plugin, 0, 1);
    }

    public static void registerCommands(BukkitCommand... commands) {
        for (BukkitCommand cmd : commands) {
            try {
                Class<?> craftServerClass = Class.forName("org.bukkit.craftbukkit." + getVersion() + ".CraftServer");
                Object craftServer = craftServerClass.cast(Bukkit.getServer());

                Method getCommandMapMethod = craftServerClass.getDeclaredMethod("getCommandMap");
                getCommandMapMethod.setAccessible(true);
                Object commandMap = getCommandMapMethod.invoke(craftServer);
                getCommandMapMethod.setAccessible(false);

                Method registerMethod = commandMap.getClass().getDeclaredMethod("register", String.class, Command.class);
                registerMethod.setAccessible(true);
                registerMethod.invoke(commandMap, cmd.getName(), cmd);
                registerMethod.setAccessible(false);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static JavaPlugin getPlugin() {
        return plugin;
    }

    private static String getVersion() {
        String packageName = Bukkit.getServer().getClass().getPackage().getName();
        return packageName.substring(packageName.lastIndexOf('.') + 1);
    }

}
