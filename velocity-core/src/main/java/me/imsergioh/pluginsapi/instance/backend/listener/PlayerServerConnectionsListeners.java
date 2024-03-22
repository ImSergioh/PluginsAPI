package me.imsergioh.pluginsapi.instance.backend.listener;

import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import me.imsergioh.pluginsapi.instance.backend.PlayerServerConnectionsHandler;

public class PlayerServerConnectionsListeners {

    @Subscribe(order = PostOrder.FIRST)
    public void onConnect(ServerConnectedEvent event) {
        PlayerServerConnectionsHandler.get(event.getPlayer()).registerCompleted(event.getServer());
    }

    @Subscribe
    public void onProxyDisconnect(DisconnectEvent event) {
        PlayerServerConnectionsHandler.unregisterCurrentConnectionRequests(event.getPlayer());
    }

}
