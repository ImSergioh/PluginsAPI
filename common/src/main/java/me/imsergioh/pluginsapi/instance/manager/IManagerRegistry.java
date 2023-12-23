package me.imsergioh.pluginsapi.instance.manager;

public interface IManagerRegistry<T> {

    void onRegister(T value);
    void onUnregister(T value);
}
