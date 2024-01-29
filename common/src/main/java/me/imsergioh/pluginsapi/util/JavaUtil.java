package me.imsergioh.pluginsapi.util;

import java.net.URLClassLoader;
import java.util.*;

@SuppressWarnings("unused")
public class JavaUtil {

    private static final Set<ClassLoader> classLoaders = new HashSet<>();

    public static void registerClassLoader(ClassLoader loader) {
        classLoaders.add(loader);
    }

    public static Class<?> findClass(String name) {
        for (ClassLoader loader : classLoaders) {
            try {
                return loader.loadClass(name);
            } catch (ClassNotFoundException ignore) {}
        }
        return null;
    }

    public static Collection<ClassLoader> getClassLoaders() {
        return classLoaders;
    }
}
