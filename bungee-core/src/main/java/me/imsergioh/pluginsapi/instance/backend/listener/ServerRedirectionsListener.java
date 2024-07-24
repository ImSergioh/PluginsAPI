package me.imsergioh.pluginsapi.instance.backend.listener;

import me.imsergioh.pluginsapi.handler.PubSubConnectionHandler;
import me.imsergioh.pluginsapi.instance.backend.request.ServerRedirectionRequest;
import me.imsergioh.pluginsapi.instance.handler.RedisPubSubListener;
import me.imsergioh.pluginsapi.manager.BungeeCordPluginsAPI;
import me.imsergioh.pluginsapi.util.ChatUtil;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.*;

public class ServerRedirectionsListener extends RedisPubSubListener {

    public static void register() {
        PubSubConnectionHandler.register(new ServerRedirectionsListener());
    }

    private ServerRedirectionsListener() {
        super(ServerRedirectionRequest.BACKEND_CHANNEL_ID);
    }

    @Override
    public void onMessage(String message) {
        ServerRedirectionRequest request = ServerRedirectionRequest.parse(message);
        if (request == null) return;
        ProxiedPlayer player = BungeeCordPluginsAPI.proxy.getPlayer(request.getId());
        if (player == null || !player.isConnected()) return;
        String serverPrefix = request.getServerPrefix();

        List<ServerInfo> list = new ArrayList<>();
        for (ServerInfo serverInfo : BungeeCordPluginsAPI.proxy.getServers().values()) {
            String serverName = serverInfo.getName();
            if (!serverName.startsWith(serverPrefix)) continue;
            list.add(serverInfo);
        }
        if (list.isEmpty()) {
            player.sendMessage(ChatUtil.parse("<red>We didn't found servers to connect to!"));
            return;
        }

        // Random selection of server by list:
        ServerInfo target = list.get(new Random().nextInt(list.size()));
        player.connect(target);
    }
}
