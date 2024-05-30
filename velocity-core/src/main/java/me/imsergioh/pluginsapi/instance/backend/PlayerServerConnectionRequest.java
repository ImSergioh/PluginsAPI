package me.imsergioh.pluginsapi.instance.backend;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import lombok.Getter;

import java.util.Timer;
import java.util.TimerTask;

@Getter
public class PlayerServerConnectionRequest {

    private final PlayerServerConnectionsHandler handler;
    private final RegisteredServer server;
    private long startDate;
    private final long timeout;
    private final int attemptsCount;

    private final Timer timer = new Timer();

    protected PlayerServerConnectionRequest(PlayerServerConnectionsHandler handler, RegisteredServer server, long timeout, int attemptsCount) {
        this.handler = handler;
        this.server = server;
        this.timeout = timeout;
        this.attemptsCount = attemptsCount;
    }

    public void connect() {
        startConnection();
    }

    public void complete() {
        timer.cancel();
    }

    private void startConnection() {
        startDate = System.currentTimeMillis();
        Player player = handler.getPlayer();
        System.out.println("Attempting to connect " + player.getUsername() + " to " + server.getServerInfo().getName());
        handler.getPlayer().createConnectionRequest(server).connect();
    }
}
