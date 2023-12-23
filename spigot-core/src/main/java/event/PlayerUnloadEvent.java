package event;

import player.CorePlayer;
import org.bukkit.entity.Player;

public class PlayerUnloadEvent extends CorePlayerEvent {

    public PlayerUnloadEvent(CorePlayer corePlayer) {
        super(corePlayer);
    }

    public Player getPlayer() {
        return getCorePlayer().get();
    }
}
