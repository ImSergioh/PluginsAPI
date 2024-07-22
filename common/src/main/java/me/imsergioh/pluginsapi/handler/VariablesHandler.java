package me.imsergioh.pluginsapi.handler;

import me.imsergioh.pluginsapi.instance.IObjectVariableListener;
import me.imsergioh.pluginsapi.instance.IVariableListener;
import me.imsergioh.pluginsapi.instance.ObjectVariableParser;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class VariablesHandler {

    private static final Set<IVariableListener<?>> legacyListeners = new HashSet<>();
    private static final Set<IObjectVariableListener<?, ?>> objectListeners = new HashSet<>();
    private static final Map<Class<?>, Set<ObjectVariableParser<?>>> parsers = new HashMap<>();

    public static String parseParsers(Object object, String message) {
        Class<?> type = object.getClass();

        Set<ObjectVariableParser<?>> parserList = parsers.get(type);

        if (parserList == null) {
            for (Class<?> keyType : parsers.keySet()) {
                if (keyType.getName().startsWith(type.getName())) {
                    parserList = parsers.get(keyType);
                    break;
                }
                if (type.getName().startsWith(keyType.getName())) {
                    parserList = parsers.get(keyType);
                    break;
                }
            }
        }
        for (ObjectVariableParser parsers : parserList) {
            message = parsers.toString(object);
        }
        return message;
    }

    public static void registerParser(ObjectVariableParser<?> parser) {
        Set<ObjectVariableParser<?>> list = parsers.getOrDefault(parser.getType(), new HashSet<>());
        list.add(parser);
        parsers.put(parser.getType(), list);
        System.out.println("REGISTERED PARSER: " + parser.getType());
    }

    public static String parse(String message) {
        if (message == null) return null;
        for (IVariableListener<?> listener : legacyListeners) {
            message = listener.parse(message);
        }
        for (IObjectVariableListener<?, ?> listener : objectListeners) {
            Object parsedObject = listener.parse(message);
            if (parsedObject == null) continue;
            message = parseParsers(parsedObject, message);
        }
        return message;
    }

    public static <T> String parse(T player, String message) {
        if (message == null) return null;
        for (IVariableListener listener : legacyListeners) {
            message = listener.parse(player, message);
        }
        for (IObjectVariableListener listener : objectListeners) {
            Object parsedObject = listener.parse(player, message);
            if (parsedObject == null) continue;
            message = parseParsers(parsedObject, message);
        }
        return message;
    }

    public static void register(IVariableListener<?> listener) {
        legacyListeners.add(listener);
    }

    public static void register(IObjectVariableListener<?, ?> listener) {
        objectListeners.add(listener);
    }

}
