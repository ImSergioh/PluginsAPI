package me.imsergioh.pluginsapi.instance.builder;

import lombok.Getter;
import org.bson.Document;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

@Getter
public class DiscordLogEmbedBuilder {

    private final Document document = new Document();

    public DiscordLogEmbedBuilder title(String title) {
        document.append("title", title);
        return this;
    }

    public DiscordLogEmbedBuilder description(String description) {
        document.append("description", description);
        return this;
    }

    public DiscordLogEmbedBuilder addField(String key, String value) {
        return addField(key, value, false);
    }

    public DiscordLogEmbedBuilder color(String color) {
        document.append("color", color);
        return this;
    }

    public DiscordLogEmbedBuilder author(String author) {
        document.append("author", author);
        return this;
    }

    public DiscordLogEmbedBuilder url(String url) {
        document.append("url", url);
        return this;
    }

    public DiscordLogEmbedBuilder image(String image) {
        document.append("image", image);
        return this;
    }

    public DiscordLogEmbedBuilder addField(String key, String value, boolean inLine) {
        Document fieldDocument = new Document();
        fieldDocument.append("key", key);
        fieldDocument.append("value", value);
        fieldDocument.append("inLine", inLine);
        List<Document> fieldsList = new ArrayList<>();
        if (document.containsKey("fields")) fieldsList.addAll(document.getList("fields", Document.class));
        fieldsList.add(fieldDocument);
        document.put("fields", fieldsList);
        return this;
    }

    public void send(Jedis jedis) {
        jedis.publish("discord-logs:embed", document.toJson());
    }

}
