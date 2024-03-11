package instance.player;

import com.velocitypowered.api.proxy.Player;
import manager.VelocityPluginsAPI;
import me.imsergioh.pluginsapi.data.player.OfflineCorePlayer;

import java.util.Optional;
import java.util.UUID;

public class VelocityCorePlayer extends OfflineCorePlayer<Player> {

    protected final Player player;

    protected VelocityCorePlayer(UUID uuid) {
        super(uuid);
        player = get();
    }

    public String getIP() {
        return get().getRemoteAddress().getAddress().toString();
    }

    @Override
    public void load() {
        super.load();
        offlineDataDocument.put("name", player.getUsername());
        offlineDataDocument.put("query_name", player.getUsername().toLowerCase());
    }

    @Override
    public Player get() {
        if (player != null) return player;
        Optional<Player> optionalPlayer = VelocityPluginsAPI.proxy.getPlayer(uuid);
        return optionalPlayer.orElse(null);

    }
}
