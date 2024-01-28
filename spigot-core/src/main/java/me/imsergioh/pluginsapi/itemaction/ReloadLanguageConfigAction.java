package me.imsergioh.pluginsapi.itemaction;

import me.imsergioh.pluginsapi.instance.ItemActionExecutor;
import me.imsergioh.pluginsapi.instance.item.ClickHandler;
import me.imsergioh.pluginsapi.language.MultiLanguageRegistry;
import me.imsergioh.pluginsapi.util.ChatUtil;
import org.bukkit.entity.Player;

public class ReloadLanguageConfigAction implements ItemActionExecutor {

    @Override
    public void execute(ClickHandler clickHandler, String label, String[] args) {
        Player player = clickHandler.player();
        MultiLanguageRegistry.reload(args[0]);
        clickHandler.player().sendMessage(ChatUtil.parse(player, "&aMensajes recargados! ({0})", args[0]));
    }
}
