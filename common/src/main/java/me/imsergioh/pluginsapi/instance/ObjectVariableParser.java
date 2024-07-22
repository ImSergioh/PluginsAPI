package me.imsergioh.pluginsapi.instance;

import lombok.Getter;

public abstract class ObjectVariableParser<O> {

    @Getter
    private final Class<O> type;

    // Constructor para inicializar el tipo de clase
    protected ObjectVariableParser(Class<O> type) {
        this.type = type;
    }

    public String toString(O o) {
        return o.toString();
    }
}
