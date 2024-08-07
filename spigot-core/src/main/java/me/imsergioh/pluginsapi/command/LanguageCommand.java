package me.imsergioh.pluginsapi.command;

import me.imsergioh.pluginsapi.language.Language;
import me.imsergioh.pluginsapi.instance.player.CorePlayer;
import me.imsergioh.pluginsapi.menu.SetLanguageMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LanguageCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = ((Player) sender).getPlayer();
        if (args.length >= 1) {
            try {
                Language language = Language.valueOf(args[0].toUpperCase());
                CorePlayer.get(((Player) sender)).setLanguage(language);
                return true;
            } catch (Exception ignore) {}
        }
        new SetLanguageMenu(player).open(player);
        return false;
    }
}

