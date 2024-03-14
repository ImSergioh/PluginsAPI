package me.imsergioh.pluginsapi.instance.backend.request;

import lombok.Getter;
import me.imsergioh.pluginsapi.connection.RedisConnection;
import org.bson.Document;

import java.util.UUID;

public class ServerRedirectionRequest extends Document {

    public static final String BACKEND_CHANNEL_ID = "playerRequest@redirection";

    private final String id;
    @Getter
    private final String username, serverPrefix;

    public ServerRedirectionRequest(UUID id, String username, String serverPrefix) {
        this.id = (String) put("id", id.toString());
        this.username = (String) put("username", username);
        this.serverPrefix = (String) put("serverPrefix", serverPrefix);
    }

    public UUID getId() {
        return UUID.fromString(id);
    }

    public void send(RedisConnection connection) {
        connection.getResource().publish(BACKEND_CHANNEL_ID, toJson());
    }

    public static ServerRedirectionRequest parse(String json) {
        System.out.println("Parsing server redirection " + json);
        try {
            Document document = Document.parse(json);
            return new ServerRedirectionRequest(UUID.fromString(document.getString("id")),
                    document.getString("username"), document.getString("serverPrefix"));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
