package me.imsergioh.pluginsapi.instance.manager;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

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

    public Set<K> keySet() {
        return registry.keySet();
    }

    public Collection<V> values() {
        return registry.values();
    }

    public boolean isRegistered(K key) {
        return registry.containsKey(key);
    }

    public V get(K key){
        return registry.get(key);
    }

}
