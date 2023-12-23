package me.imsergioh.pluginsapi.data.managment;

public interface IFactoryValue<O> {

    void load();
    void unload();

    O get();
}
