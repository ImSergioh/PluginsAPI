package me.imsergioh.pluginsapi.instance;

public interface IVariableListener<Player> {

    String parse(String message);
    String parse(Player player, String message);
}
