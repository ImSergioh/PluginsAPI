package me.imsergioh.pluginsapi.instance.player;

import lombok.Getter;
import lombok.Setter;
import me.imsergioh.pluginsapi.SpigotPluginsAPI;
import me.imsergioh.pluginsapi.data.player.OfflineCorePlayer;
import me.imsergioh.pluginsapi.event.PlayerTickEvent;
import me.imsergioh.pluginsapi.handler.LanguagesHandler;
import me.imsergioh.pluginsapi.instance.PlayerLanguages;
import me.imsergioh.pluginsapi.language.Language;
import me.imsergioh.pluginsapi.event.PlayerLanguageChangeEvent;
import me.imsergioh.pluginsapi.event.PlayerLanguageChangedEvent;
import me.imsergioh.pluginsapi.instance.menu.CoreMenu;
import me.imsergioh.pluginsapi.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import me.imsergioh.pluginsapi.util.SyncUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class CorePlayer extends OfflineCorePlayer<Player> {

    @Setter
    @Getter
    private static boolean enabledTickEvent = false;

    protected static final Map<UUID, CorePlayer> players = new ConcurrentHashMap<>();

    @Getter
    protected final UUID uuid;
    @Getter
    protected final Player bukkitPlayer;
    protected boolean isOnline;

    @Getter
    protected final CorePlayerData playerData;

    @Getter
    @Setter
    private CoreMenu currentMenuOpen;
    @Getter
    private CoreMenu previousOpenMenu;
    @Getter
    @Setter
    private CoreMenu currentMenuSet;

    public CorePlayer(UUID uuid) {
        super(uuid);
        this.uuid = uuid;
        this.bukkitPlayer = Bukkit.getPlayer(uuid);
        this.playerData = new CorePlayerData(this);
        players.put(uuid, this);
        load();
        setupIfPlayerIsOnline();
    }

    private void setupIfPlayerIsOnline() {
        if (bukkitPlayer == null) return;
        if (!bukkitPlayer.isOnline()) return;
        isOnline = true;
        checkTickTask();
    }

    public boolean canTickTask() {
        return true;
    }

    public void clearInventory() {
        bukkitPlayer.getInventory().clear();
        currentMenuSet = null;
    }

    public void registerMenuHistory(CoreMenu coreMenu) {
        if (previousOpenMenu != null) {
            if (previousOpenMenu.getClass().getName().equals(coreMenu.getClass().getName())) return;
        }
        previousOpenMenu = coreMenu;
    }

    public void openPreviousMenu() {
        if (previousOpenMenu != null) {
            previousOpenMenu.open(bukkitPlayer);
        }
    }

    @Override
    public void unload() {
        PlayerLanguages.unregister(uuid);
        playerData.save();
        super.unload();
    }

    public UUID getUUID() {
        return uuid;
    }

    @Override
    public Player get() {
        return bukkitPlayer;
    }

    public void setLanguage(Language language) {
        Language previousLang = getLanguage();
        if (previousLang.equals(language)) return;
        Bukkit.getPluginManager()
                .callEvent(new PlayerLanguageChangeEvent(this, previousLang, language));
        playerData.setData("lang", language.name());
        PlayerLanguages.register(uuid, language);
        sendLanguageMessage("general", "lang_change");
        if (bukkitPlayer != null)
            SyncUtil.async(() -> {
                Bukkit.getPluginManager()
                        .callEvent(new PlayerLanguageChangedEvent(this, previousLang, language));
            });
    }

    public void sendLanguageMessage(String holderName, String key, Object... vars) {
        if (bukkitPlayer == null) return;
        bukkitPlayer.sendMessage(getLanguageMessage(holderName, key, vars));
    }

    public String getLanguageMessage(String holderName, String key, Object... vars) {
        String message = null;
        try {
            message = LanguagesHandler.get(getLanguage())
                    .get(holderName).getString(key);
        } catch (Exception ignore) {
        }
        return message == null ? "undefined" : ChatUtil.parse(bukkitPlayer, message, vars);
    }

    public Language getLanguage() {
        return PlayerLanguages.get(bukkitPlayer.getUniqueId());
    }

    public void tickTask() {
        if (!canTickTask()) return;
        Bukkit.getPluginManager().callEvent(new PlayerTickEvent(this));
    }

    private static void checkTickTask() {
        if (!enabledTickEvent) return;
        SpigotPluginsAPI.startTickTask();
    }

    public static void remove(UUID uuid) {
        players.remove(uuid);
    }

    public static CorePlayer get(UUID uuid) {
        return players.get(uuid);
    }

    public static CorePlayer get(Player player) {
        return get(player.getUniqueId());
    }

    public static Collection<CorePlayer> getCorePlayers() {
        return players.values();
    }

}
