package me.imsergioh.pluginsapi.connection;

import lombok.Getter;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisConnection extends JedisPool {

    public static RedisConnection mainConnection;

    private Jedis jedis;
    @Getter
    private final String host;
    @Getter
    private final int port;

    public RedisConnection(String host, int port) {
        super(buildPoolConfig(), host, port);
        System.out.println("Connecting to redis " + host + ":" + port);
        this.host = host;
        this.port = port;
        if (mainConnection != null) {
            mainConnection = this;
        }
    }

    public void send(String channel, String message) {
        try (Jedis jedis = getResource()) {
            jedis.publish(channel, message);
        }
    }

    @Override
    public Jedis getResource() {
        try {
            if (jedis == null) {
                jedis = new Jedis(host, port);
            }
            return jedis;
        } catch (Exception e) {
            jedis = new Jedis(host, port);
        }
        return jedis;
    }

    private static JedisPoolConfig buildPoolConfig() {
        return new JedisPoolConfig();
    }
}
