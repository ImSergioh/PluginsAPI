package me.imsergioh.pluginsapi.data.storage;

import org.bson.Document;

public interface IDataLoader {

    Document load();
    Document save();
}
