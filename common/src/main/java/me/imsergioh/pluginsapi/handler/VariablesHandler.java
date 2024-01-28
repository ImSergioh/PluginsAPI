package me.imsergioh.pluginsapi.handler;

import me.imsergioh.pluginsapi.instance.IVariableListener;

import java.util.HashSet;
import java.util.Set;

public class VariablesHandler {

    private static final Set<IVariableListener> listeners = new HashSet<>();

    public static String parse(String message) {
        if (message == null) return null;
        for (IVariableListener listener : listeners) {
            message = listener.parse(message);
        }
        return message;
    }

    public static <Player> String parse(Player player, String message) {
        if (message == null) return null;
        for (IVariableListener<Player> listener : listeners) {
            message = listener.parse(player, message);
        }
        return message;
    }

    public static void register(IVariableListener listener) {
        listeners.add(listener);
    }

}
