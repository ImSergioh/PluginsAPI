package me.imsergioh.pluginsapi.instance;

public interface IPluginConfig {

    IPluginConfig save();
    IPluginConfig load();
    IPluginConfig registerDefault(String path, Object value);
}
