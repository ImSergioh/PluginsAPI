package itemaction;

import instance.ItemActionExecutor;
import item.ClickHandler;
import player.CorePlayer;

public class OpenPreviousAction implements ItemActionExecutor {

    @Override
    public void execute(ClickHandler clickHandler, String label, String[] args) {
        CorePlayer corePlayer = CorePlayer.get(clickHandler.player());
        corePlayer.openPreviousMenu();
    }
}
