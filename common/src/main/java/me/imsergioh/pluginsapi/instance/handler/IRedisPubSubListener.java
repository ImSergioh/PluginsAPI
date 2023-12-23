package me.imsergioh.pluginsapi.instance.handler;

public interface IRedisPubSubListener {

    void onMessage(String message);

    String getChannel();
}
