package itemaction;

import org.bukkit.entity.Player;
import manager.MenusManager;
import instance.ItemActionExecutor;
import item.ClickHandler;

public class SetInventoryAction implements ItemActionExecutor {

    @Override
    public void execute(ClickHandler clickHandler, String label, String[] args) {
        Player player = clickHandler.getPlayer();
        String name = args[0];
        MenusManager.loadForPlayer(player, name).set(player);
    }
}
