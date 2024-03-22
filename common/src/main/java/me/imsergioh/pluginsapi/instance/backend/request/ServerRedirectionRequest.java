package me.imsergioh.pluginsapi.instance.backend.request;

import lombok.Getter;
import me.imsergioh.pluginsapi.connection.RedisConnection;
import org.bson.Document;

import java.util.UUID;

@Getter
public class ServerRedirectionRequest extends Document {

    public static final String BACKEND_CHANNEL_ID = "playerRequest@redirection";

    private final UUID id;
    private final String username, serverPrefix;

    public ServerRedirectionRequest(UUID id, String username, String serverPrefix) {
        this.id = id;
        this.username = username;
        this.serverPrefix = serverPrefix;
        put("id", id.toString());
        put("username", username);
        put("serverPrefix", serverPrefix);
    }

    public void send(RedisConnection connection) {
        connection.getResource().publish(BACKEND_CHANNEL_ID, toJson());
    }

    public static ServerRedirectionRequest parse(String json) {
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
