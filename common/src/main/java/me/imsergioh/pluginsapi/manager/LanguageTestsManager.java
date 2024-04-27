package me.imsergioh.pluginsapi.manager;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import me.imsergioh.pluginsapi.instance.FilePluginConfig;
import org.bson.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

public class LanguageTestsManager {

    public static void main(String[] args) throws IOException {
        Collection<FilePluginConfig> configs = readAllConfigsInResources("language");
        System.out.println(configs);
    }

    private static Collection<FilePluginConfig> readAllConfigsInResources(String path) throws IOException {
        Set<FilePluginConfig> configs = new HashSet<>();
        Enumeration<URL> enumeration = LanguageTestsManager.class.getClassLoader().getResources(path);
        URL url;
        while (enumeration.hasMoreElements()) {
            url = enumeration.nextElement();
            InputStream inputStream = LanguageTestsManager.class.getClassLoader().getResourceAsStream(url.getPath());
            LinkedTreeMap<String, Object> map = read(inputStream);
            if (map == null) {
                System.out.println("Could not read the file for " + url.getPath());
            }
            FilePluginConfig config = new FilePluginConfig(new Document(map));
            configs.add(config);
        }
        return configs;
    }

    public static LinkedTreeMap<String, Object> read(InputStream inputStream) {
        if (inputStream != null) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line);
                }
                String json = content.toString();
                Gson gson = new Gson();
                return gson.fromJson(json, LinkedTreeMap.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No se pudo encontrar el archivo JSON dentro del JAR.");
        }
        return null;
    }

}
