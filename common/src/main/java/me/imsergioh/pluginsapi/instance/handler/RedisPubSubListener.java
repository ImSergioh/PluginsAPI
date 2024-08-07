package me.imsergioh.pluginsapi.instance.handler;

import lombok.Getter;
import redis.clients.jedis.JedisPubSub;

@Getter
public abstract class RedisPubSubListener extends JedisPubSub implements IRedisPubSubListener {

    protected final String channel;

    public RedisPubSubListener(String channel) {
        this.channel = channel;
    }

    @Override
    public void onMessage(String channel, String message) {
        onMessage(message);
    }

}
