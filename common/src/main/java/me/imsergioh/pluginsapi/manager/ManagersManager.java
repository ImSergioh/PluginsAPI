package me.imsergioh.pluginsapi.manager;

import me.imsergioh.pluginsapi.instance.manager.PluginManager;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class ManagersManager {

    private final HashMap<String, PluginManager> managers = new HashMap<>();

    public <T extends PluginManager> T registerManager(Class<T> managerClass, Object... initArgs) {
        try {
            PluginManager pluginManager = (PluginManager) managerClass.getDeclaredConstructors()[0].newInstance(initArgs);
            managers.put(managerClass.getName(), pluginManager);
            pluginManager.load();
            return managerClass.cast(pluginManager);
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @SafeVarargs
    public final void unregisterManagers(Class<? extends PluginManager>... managerClasses) {
        for (Class<? extends PluginManager> managerClass : managerClasses) {
            PluginManager pluginManager = managers.get(managerClass.getName());
            if (pluginManager == null) continue;
            pluginManager.unload();
            managers.remove(managerClass.getName());
        }
    }

    public <T extends PluginManager> T of(Class<T> managerClass) {
        String name = managerClass.getName();
        if (managers.containsKey(name)) {
            PluginManager manager = managers.get(name);
            return managerClass.cast(manager);
        }
        return null;
    }

    public void unregisterAll() {
        for (String className : managers.keySet()) {
            PluginManager pluginManager = managers.get(className);
            pluginManager.unload();
            managers.remove(className);
        }
    }
}
