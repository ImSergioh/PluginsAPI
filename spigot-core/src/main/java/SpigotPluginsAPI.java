import manager.ItemActionsManager;
import command.LanguageCommand;
import command.RestartCommand;
import item.ItemBuilder;
import listener.CorePlayerListeners;
import message.LanguageMessages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Method;

public class SpigotPluginsAPI {

    private static JavaPlugin plugin;

    public static void setup(JavaPlugin pl) {
        plugin = pl;
        LanguageMessages.setup();
        ItemBuilder.setup(plugin);
        ItemActionsManager.registerDefaults();

        registerCommands(new RestartCommand("crestart"));
        Bukkit.getPluginManager().registerEvents(new CorePlayerListeners(), pl);

        PluginCommand languageCommand = plugin.getCommand("language");
        if (languageCommand != null) {
            languageCommand.setExecutor(new LanguageCommand());
        } else {
            System.out.println("Command 'language' not found! (Not registered)");
        }
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
