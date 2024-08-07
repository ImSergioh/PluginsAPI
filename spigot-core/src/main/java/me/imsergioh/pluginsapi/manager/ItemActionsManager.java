package me.imsergioh.pluginsapi.manager;

import me.imsergioh.pluginsapi.instance.ItemActionExecutor;
import me.imsergioh.pluginsapi.instance.item.ClickHandler;
import me.imsergioh.pluginsapi.itemaction.*;

import java.util.HashMap;
import java.util.List;

public class ItemActionsManager {

    private static final HashMap<String, ItemActionExecutor> commands = new HashMap<>();

    public static void registerDefaults() {
        registerCommand("msg", new MessageAction());
        registerCommand("setInv", new SetInventoryAction());
        registerCommand("openInv", new OpenInventoryAction());
        registerCommand("closeInv", new CloseInventoryAction());
        registerCommand("openPrevious", new OpenPreviousAction());
        registerCommand("reloadLanguageConfig", new ReloadLanguageConfigAction());
        registerCommand("cmd", new CommandAction());
    }

    public static void registerCommand(String name, ItemActionExecutor executor) {
        commands.put(name.toLowerCase(), executor);
    }

    public static boolean execute(ClickHandler handler, List<String> labelActions) {
        if (labelActions == null) return false;
        boolean executed = false;
        for (String cmdLabel : labelActions) {
            String name = cmdLabel.split(" ")[0];
            String[] args = cmdLabel.replaceFirst(name + " ", "").split(" ");
            if (!commands.containsKey(name.toLowerCase())) continue;
            commands.get(name.toLowerCase()).execute(handler, cmdLabel, args);
            executed = true;
        }
        return executed;
    }
}
