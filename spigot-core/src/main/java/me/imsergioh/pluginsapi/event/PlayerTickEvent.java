package me.imsergioh.pluginsapi.event;

import me.imsergioh.pluginsapi.instance.player.CorePlayer;

public class PlayerTickEvent extends CorePlayerEvent {
    
    public PlayerTickEvent(CorePlayer corePlayer) {
        super(corePlayer);
    }
}
