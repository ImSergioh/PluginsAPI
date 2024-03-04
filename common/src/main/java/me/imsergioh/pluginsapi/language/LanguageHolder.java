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

    public LanguageHolder(Language language) {
        this.language = language;
        this.dir = new File(FOLDER_PATH + "/" + language.name().toUpperCase());
        if (!dir.exists()) dir.mkdirs();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                long currentMillis = System.currentTimeMillis();
                Set<String> toRemove = new HashSet<>();
                for (String holderName : messagesHolders.keySet()) {
                    LanguageMessagesHolder holder = messagesHolders.get(holderName);
                    long difference = currentMillis - holder.getExpirationMillis();
                    if (difference > LanguageMessagesHolder.CLEAR_MESSAGE_HOLDER_AFTER) {
                        toRemove.add(holderName);
                    }
                }
                for (String name : toRemove) {
                    messagesHolders.remove(name);
                    System.out.println("Removed from cache LanguageMessagesHolder -> " + name);
                }

            }
        }, LanguageMessagesHolder.CLEAR_MESSAGE_HOLDER_AFTER, LanguageMessagesHolder.CLEAR_MESSAGE_HOLDER_AFTER);
    }

    public LanguageMessagesHolder register(String name) {
        LanguageMessagesHolder holder = new LanguageMessagesHolder(name, this);
        messagesHolders.put(name, holder);
        return holder;
    }

    public LanguageMessagesHolder get(String name) {
        if (!messagesHolders.containsKey(name)) {
            register(name);
        }
        LanguageMessagesHolder holder = messagesHolders.get(name);
        holder.registerUsage();
        return holder;
    }
}
