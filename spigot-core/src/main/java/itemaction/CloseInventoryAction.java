package itemaction;

import instance.ItemActionExecutor;
import item.ClickHandler;

public class CloseInventoryAction implements ItemActionExecutor {

    @Override
    public void execute(ClickHandler clickHandler, String label, String[] args) {
        clickHandler.player().closeInventory();
    }
}
