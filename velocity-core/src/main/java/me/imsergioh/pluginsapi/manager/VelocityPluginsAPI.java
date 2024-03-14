package me.imsergioh.pluginsapi.manager;

import com.velocitypowered.api.proxy.ProxyServer;
import me.imsergioh.pluginsapi.instance.backend.listener.ServerRedirectionsListener;

public class VelocityPluginsAPI {

    public static ProxyServer proxy;

    public static void setup(ProxyServer server) {
        proxy = server;
        ServerRedirectionsListener.register();
    }

}
