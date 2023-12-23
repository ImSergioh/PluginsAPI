package me.imsergioh.pluginsapi.instance;

import me.imsergioh.pluginsapi.instance.item.ClickHandler;

public interface ItemActionExecutor {

    void execute(ClickHandler clickHandler, String label, String[] args);
}
