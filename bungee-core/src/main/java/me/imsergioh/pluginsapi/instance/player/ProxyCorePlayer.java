package me.imsergioh.pluginsapi.instance.player;

import me.imsergioh.pluginsapi.data.player.OfflineCorePlayer;
import me.imsergioh.pluginsapi.manager.BungeeCordPluginsAPI;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;

public class ProxyCorePlayer extends OfflineCorePlayer<ProxiedPlayer> {

    protected final ProxiedPlayer player;

    protected ProxyCorePlayer(UUID uuid) {
        super(uuid);
        player = get();
    }

    public String getIP() {
        return get().getPendingConnection().getSocketAddress().toString();
    }

    @Override
    public void load() {
        super.load();
        offlineDataDocument.put("name", player.getName());
        offlineDataDocument.put("query_name", player.getName().toLowerCase());
    }

    @Override
    public ProxiedPlayer get() {
        if (player != null) return player;
        return BungeeCordPluginsAPI.proxy.getPlayer(uuid);
    }
}
