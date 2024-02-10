package me.imsergioh.pluginsapi.instance;

import com.google.gson.GsonBuilder;
import lombok.Getter;
import me.imsergioh.pluginsapi.util.ConfigUtil;
import me.imsergioh.pluginsapi.util.FileUtil;
import org.bson.Document;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Getter
public class FilePluginConfig extends Document implements IPluginConfig {

    private final String filePath;

    public FilePluginConfig(String filePath) {
        this(new File(filePath));
    }

    public FilePluginConfig(File file) {
        this.filePath = file.getAbsolutePath();
        FileUtil.setup(file);
    }

    @Override
    public FilePluginConfig save() {
        try {
            // CONVERT
            GsonBuilder gsonBuilder = new GsonBuilder();
            String json = gsonBuilder.setPrettyPrinting().disableHtmlEscaping()
                    .create().toJson(this);

            // SAVE
            FileWriter writer = new FileWriter(filePath);
            writer.write(json);
            writer.close();
            return this;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public FilePluginConfig load() {
        try {
            Document document = ConfigUtil.convertFileToDocument(filePath);
            if (document != null) putAll(document);
        } catch (Exception ignore) {}
        return this;
    }

    @Override
    public Document put(String key, Object value) {
        if (key.contains(".")) {
            String[] keys = key.split("\\.");
            Document current = this;
            for (int i = 0; i < keys.length - 1; i++) {
                String part = keys[i];
                Document next = (Document) current.get(part);
                if (next == null) {
                    next = new Document();
                    current.put(part, next);
                }
                current = next;
            }
            current.put(keys[keys.length - 1], value);
        } else {
            super.put(key, value);
        }
        return this;
    }

    @Override
    public Object get(Object key) {
        if (key instanceof String && ((String) key).contains(".")) {
            String[] keys = ((String) key).split("\\.");
            Document current = this;
            for (String part : keys) {
                Object value = current.get(part);
                if (value instanceof Document) {
                    current = (Document) value;
                } else {
                    return value;
                }
            }
            return current;
        } else {
            return super.get(key);
        }
    }

    @Override
    public IPluginConfig registerDefault(String path, Object value) {
        if (containsKey(path)) return this;
        this.put(path, value);
        return this;
    }

}
