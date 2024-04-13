package me.imsergioh.pluginsapi.itemaction;

import me.imsergioh.pluginsapi.instance.ItemActionExecutor;
import me.imsergioh.pluginsapi.instance.item.ClickHandler;
import me.imsergioh.pluginsapi.util.ChatUtil;
import me.imsergioh.pluginsapi.util.PaperChatUtil;
import org.bukkit.entity.Player;

public class MessageAction implements ItemActionExecutor {

    @Override
    public void execute(ClickHandler clickHandler, String label, String[] args) {
        Player player = clickHandler.getPlayer();
        StringBuilder messageBuilder = new StringBuilder();

        for (String word : args) {
            messageBuilder.append(word).append(" ");
        }
        messageBuilder.deleteCharAt(messageBuilder.length() - 1);
        player.sendMessage(PaperChatUtil.parse(player, messageBuilder.toString()));
    }
}
