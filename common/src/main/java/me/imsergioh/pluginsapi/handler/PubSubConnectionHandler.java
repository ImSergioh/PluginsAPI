package me.imsergioh.pluginsapi.handler;

import me.imsergioh.pluginsapi.connection.RedisConnection;
import me.imsergioh.pluginsapi.instance.handler.IRedisPubSubListener;
import me.imsergioh.pluginsapi.instance.handler.RedisPubSubListener;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.HashMap;

public class PubSubConnectionHandler extends JedisPubSub {
    
    private static final HashMap<String, IRedisPubSubListener> mapListeners = new HashMap<>();

    public static void register(RedisPubSubListener... listeners) {
        for (RedisPubSubListener listener : listeners) {
            try(Jedis jedis = new Jedis(RedisConnection.mainConnection.getHost(), RedisConnection.mainConnection.getPort())) {
                new Thread(() -> {jedis.subscribe(listener, listener.getChannel());}).start();
            }
        }
    }

    @Override
    public void onMessage(String channel, String message) {
        if (!mapListeners.containsKey(channel)) return;
        mapListeners.get(channel).onMessage(message);
    }
}
