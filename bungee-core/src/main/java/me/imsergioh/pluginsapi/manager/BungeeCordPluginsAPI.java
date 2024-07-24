package me.imsergioh.pluginsapi.manager;

import lombok.Getter;
import me.imsergioh.pluginsapi.instance.backend.listener.ServerRedirectionsListener;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

public class BungeeCordPluginsAPI {

    public static ProxyServer proxy;

    @Getter
    private static Plugin plugin;

    public static void setup(Plugin p, ProxyServer server) {
        proxy = server;
        plugin = p;
        ServerRedirectionsListener.register();
    }

}
