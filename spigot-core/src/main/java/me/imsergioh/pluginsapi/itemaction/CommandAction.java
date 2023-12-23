package me.imsergioh.pluginsapi.itemaction;

import me.imsergioh.pluginsapi.instance.ItemActionExecutor;
import me.imsergioh.pluginsapi.instance.item.ClickHandler;

public class CommandAction implements ItemActionExecutor {

    @Override
    public void execute(ClickHandler clickHandler, String label, String[] args) {
        String name = label.split(" ")[0];
        label = label.replaceFirst(name + " ", "");
        clickHandler.player().performCommand(label);
    }
}
