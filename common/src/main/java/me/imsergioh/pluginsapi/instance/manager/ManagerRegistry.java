package me.imsergioh.pluginsapi.instance.manager;

import java.util.HashMap;

public abstract class ManagerRegistry<K, V> implements IManagerRegistry<V>, PluginManager {

    private final HashMap<K, V> registry = new HashMap<>();

    @Override
    public void onRegister(V value) {}

    @Override
    public void onUnregister(V value) {}

    public void register(K key, V value) {
        if (registry.containsKey(key)) return;
        registry.put(key, value);
        onRegister(value);
    }

    public void unregister(K key) {
        if (!registry.containsKey(key)) return;
        onUnregister(registry.remove(key));
    }

}
