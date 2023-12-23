package itemaction;

import org.bukkit.entity.Player;
import instance.ItemActionExecutor;
import item.ClickHandler;
import us.smartmc.core.pluginsapi.util.ChatUtil;

public class MessageAction implements ItemActionExecutor {

    @Override
    public void execute(ClickHandler clickHandler, String label, String[] args) {
        Player player = clickHandler.getPlayer();
        StringBuilder messageBuilder = new StringBuilder(args[0]);
        for (String word : args) {
            messageBuilder.append(word).append(" ");
        }
        messageBuilder.append(messageBuilder.length()-1);
        player.sendMessage(ChatUtil.parse(player, messageBuilder.toString()));
    }
}
