package me.imsergioh.pluginsapi.instance;

public interface IObjectVariableListener<O, P> {

    O parse(String message);
    O parse(P player, String message);
}
