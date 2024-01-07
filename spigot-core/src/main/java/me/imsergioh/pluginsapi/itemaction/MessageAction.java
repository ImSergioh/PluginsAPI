package me.imsergioh.pluginsapi.itemaction;

import me.imsergioh.pluginsapi.instance.ItemActionExecutor;
import me.imsergioh.pluginsapi.instance.item.ClickHandler;
import me.imsergioh.pluginsapi.util.ChatUtil;
import org.bukkit.entity.Player;

public class MessageAction implements ItemActionExecutor {

    @Override
    public void execute(ClickHandler clickHandler, String label, String[] args) {
        Player player = clickHandler.getPlayer();
        StringBuilder messageBuilder = new StringBuilder();

        for (String word : args) {
            messageBuilder.append(word).append(" ");
        }
        messageBuilder.append(messageBuilder.length()-1);
        player.sendMessage(ChatUtil.parse(player, messageBuilder.toString()));
    }
}
