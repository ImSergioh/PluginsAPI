package me.imsergioh.pluginsapi.language;

import lombok.Getter;

import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class LanguageHolder {

    private static final String FOLDER_PATH = "/home/lang";
    @Getter
    private final File dir;
    @Getter
    private final Language language;

    private final Map<String, LanguageMessagesHolder> messagesHolders = new HashMap<>();
    private final Timer timer = new Timer();

    public LanguageHolder(Language language) {
        this.language = language;
        this.dir = new File(FOLDER_PATH + "/" + language.name().toUpperCase());
        if (!dir.exists()) dir.mkdirs();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                long currentMillis = System.currentTimeMillis();
                List<String> toRemove = new ArrayList<>();
                for (String holderName : messagesHolders.keySet()) {
                    LanguageMessagesHolder holder = messagesHolders.get(holderName);
                    long difference = currentMillis - holder.getExpirationMillis();
                    if (difference > LanguageMessagesHolder.CLEAR_MESSAGE_HOLDER_AFTER) {
                        toRemove.add(holderName);
                    }
                }
                for (String name : toRemove) {
                    messagesHolders.remove(name);
                }
            }
        }, LanguageMessagesHolder.CLEAR_MESSAGE_HOLDER_AFTER, LanguageMessagesHolder.CLEAR_MESSAGE_HOLDER_AFTER);
    }

    public LanguageMessagesHolder register(String name) {
        return messagesHolders.put(name, new LanguageMessagesHolder(name, this));
    }

    public LanguageMessagesHolder get(String name) {
        if (!messagesHolders.containsKey(name)) return register(name);
        LanguageMessagesHolder holder = messagesHolders.get(name);
        holder.registerUsage();
        return holder;
    }
}
