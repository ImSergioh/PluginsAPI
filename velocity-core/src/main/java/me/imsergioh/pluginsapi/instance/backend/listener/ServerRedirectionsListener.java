package me.imsergioh.pluginsapi.instance.backend.listener;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import me.imsergioh.pluginsapi.handler.PubSubConnectionHandler;
import me.imsergioh.pluginsapi.instance.backend.PlayerServerConnectionsHandler;
import me.imsergioh.pluginsapi.instance.backend.request.ServerRedirectionRequest;
import me.imsergioh.pluginsapi.instance.handler.RedisPubSubListener;
import me.imsergioh.pluginsapi.manager.VelocityPluginsAPI;
import me.imsergioh.pluginsapi.util.ChatUtil;
import me.imsergioh.pluginsapi.util.VelocityChatUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

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
        Optional<Player> optional = VelocityPluginsAPI.proxy.getPlayer(request.getId());
        if (optional.isEmpty()) return;
        Player player = optional.get();
        String serverPrefix = request.getServerPrefix();

        List<RegisteredServer> list = new ArrayList<>();
        for (RegisteredServer server : VelocityPluginsAPI.proxy.getAllServers()) {
            if (!server.getServerInfo().getName().startsWith(serverPrefix)) continue;
            list.add(server);
        }
        if (list.isEmpty()) {
            player.sendMessage(VelocityChatUtil.parse("<red>We didn't found servers to connect to!"));
            return;
        }

        // Random selection of server by list:
        RegisteredServer target = list.get(new Random().nextInt(list.size()));
        if (target != null) {
            PlayerServerConnectionsHandler.get(player).sendConnectionQueue(target);
        } else {
            player.sendMessage(Component.text(NamedTextColor.RED + "Servers not found."));
        }
    }
}
