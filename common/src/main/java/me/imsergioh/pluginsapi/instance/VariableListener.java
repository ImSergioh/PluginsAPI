package me.imsergioh.pluginsapi.instance;

public abstract class VariableListener<P> implements IVariableListener<P> {

    public boolean isValid(P toCheck) {
        return toCheck != null;
    }

}
