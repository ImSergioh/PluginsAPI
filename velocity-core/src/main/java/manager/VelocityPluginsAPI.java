package manager;

import com.velocitypowered.api.proxy.ProxyServer;

public class VelocityPluginsAPI {

    public static ProxyServer proxy;

    public static void setup(ProxyServer server) {
        proxy = server;
    }

}
