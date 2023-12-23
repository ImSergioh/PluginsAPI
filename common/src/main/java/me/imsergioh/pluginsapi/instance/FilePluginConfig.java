package me.imsergioh.pluginsapi.instance;

import com.google.gson.GsonBuilder;
import lombok.Getter;
import me.imsergioh.pluginsapi.util.ConfigUtil;
import me.imsergioh.pluginsapi.util.FileUtil;
import org.bson.Document;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
    public FilePluginConfig registerDefault(String path, Object value) {
        putIfAbsent(path, value);
        return this;
    }

}
