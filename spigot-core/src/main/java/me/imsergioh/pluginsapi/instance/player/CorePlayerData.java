package me.imsergioh.pluginsapi.instance.player;

import com.mongodb.client.MongoCollection;
import lombok.Getter;
import me.imsergioh.pluginsapi.SpigotPluginsAPI;
import me.imsergioh.pluginsapi.connection.MongoDBConnection;
import me.imsergioh.pluginsapi.event.FirstCorePlayerJoinEvent;
import me.imsergioh.pluginsapi.event.PlayerDataLoadedEvent;
import me.imsergioh.pluginsapi.instance.PlayerLanguages;
import me.imsergioh.pluginsapi.language.Language;
import me.imsergioh.pluginsapi.util.SyncUtil;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

@Getter
public class CorePlayerData {

    private final CorePlayer corePlayer;

    private Document document;

    public CorePlayerData(CorePlayer corePlayer) {
        this.corePlayer = corePlayer;
        document = new Document("_id", corePlayer.getUUID().toString());
        new BukkitRunnable() {
            @Override
            public void run() {
                loadData();
                registerData("firstLogin", System.currentTimeMillis());
                document.put("lastLogin", System.currentTimeMillis());
            }
        }.runTaskAsynchronously(SpigotPluginsAPI.getPlugin());
    }

    public CorePlayerData(UUID uuid) {
        this.corePlayer = CorePlayer.get(uuid);
        document = new Document("_id", uuid.toString());
        loadData();
    }

    public void registerData(String path, Object value) {
        if (document.containsKey(path)) return;
        document.put(path, value);
    }

    public void setData(String path, Object value) {
        document.put(path, value);
    }

    private void loadData() {
        // QUERY DOCUMENT MONGODB AND SET IF FOUND
        Document queryDocument = document;
        Document document = MongoDBConnection.mainConnection
                .getDatabase("player_data")
                .getCollection("core_players").find(queryDocument).first();
        if (document != null) {
            this.document = document;
        } else if (corePlayer != null) {
            // Document not found: Calls first core player join event (if connected)
            Bukkit.getScheduler().runTask(SpigotPluginsAPI.getPlugin(), () -> {
                Bukkit.getPluginManager().callEvent(new FirstCorePlayerJoinEvent(corePlayer));
            });
        }

        // Return if not connected
        if (corePlayer == null) return;

        // Register language & call event PlayerDataLoadedEvent
        String langString = this.document.getString("lang");
        Language language = null;
        try {
            language = Language.valueOf(langString);
        } catch (Exception e) {
            language = Language.getDefault();
            this.document.put("lang", language.name());
        }
        PlayerLanguages.register(corePlayer.getUUID(), language);
        registerData("lang", PlayerLanguages.get(corePlayer.getUUID()).name());

        Bukkit.getScheduler().runTaskLater(SpigotPluginsAPI.getPlugin(), () -> {
            Bukkit.getPluginManager().callEvent(new PlayerDataLoadedEvent(corePlayer, this));
        }, 2);
    }


    public void save() {
        // GET COLLECTION
        MongoCollection<Document> collection = MongoDBConnection.mainConnection.getDatabase("player_data")
                .getCollection("core_players");

        // FILTER DOCUMENT (CREATE)
        Document queryDocument = new Document("_id", corePlayer.getUUID().toString());

        // DELETE
        collection.deleteMany(queryDocument);

        // INSERT DOCUMENT AGAIN
        collection.insertOne(document);
    }

}
