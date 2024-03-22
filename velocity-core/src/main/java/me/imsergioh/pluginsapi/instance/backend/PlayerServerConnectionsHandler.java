package me.imsergioh.pluginsapi.instance.backend;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import lombok.Getter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerServerConnectionsHandler {

    private static final Map<UUID, PlayerServerConnectionsHandler> handlers = new HashMap<>();

    @Getter
    private final Player player;
    private final Map<RegisteredServer, PlayerServerConnectionRequest> requests = new LinkedHashMap<>();

    private PlayerServerConnectionsHandler(Player player) {
        this.player = player;
    }

    public void registerCompleted(RegisteredServer server) {
        System.out.println("Register completed " + server.getServerInfo().getName());
        PlayerServerConnectionRequest request = requests.remove(server);
        if (request != null)
            request.complete();
    }

    public boolean isQueued(RegisteredServer server) {
        return requests.containsKey(server);
    }

    public boolean isConnectingToAnotherServer() {
        return !requests.isEmpty();
    }

    public void sendConnectionQueue(RegisteredServer server) {
        sendConnectionQueue(server, 1000, 3);
    }

    protected void sendConnectionQueue(RegisteredServer server, long timeout, int attempts) {
        PlayerServerConnectionRequest request = new PlayerServerConnectionRequest(this, server, timeout, attempts);
        requests.put(server, request);
        request.connect();
    }

    public static PlayerServerConnectionsHandler get(Player player) {
        PlayerServerConnectionsHandler handler = handlers.get(player.getUniqueId());
        if (handler == null) {
            handlers.put(player.getUniqueId(), new PlayerServerConnectionsHandler(player));
        }
        return handlers.get(player.getUniqueId());
    }

    public void unregisterCurrentConnectionRequests() {
        for (PlayerServerConnectionRequest request : requests.values()) {
            request.complete();
        }
    }

    public static void unregisterCurrentConnectionRequests(Player player) {
        PlayerServerConnectionsHandler handler = handlers.remove(player.getUniqueId());
        if (handler == null) return;
        handler.unregisterCurrentConnectionRequests();
    }
}
