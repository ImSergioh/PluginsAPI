package me.imsergioh.pluginsapi.instance.exceptionlistener;

import me.imsergioh.pluginsapi.connection.RedisConnection;
import me.imsergioh.pluginsapi.instance.builder.DiscordLogEmbedBuilder;
import me.imsergioh.pluginsapi.instance.handler.ExceptionHandlerListener;

import java.util.*;

public class SendExceptionToDiscordListener implements ExceptionHandlerListener {

    private static final Set<String> alreadySent = new HashSet<>();
    private final Map<String, String> additionalFields = new HashMap<>();

    public SendExceptionToDiscordListener addField(String name, String value) {
        return addField(name, value, false);
    }

    public SendExceptionToDiscordListener addField(String name, String value, boolean inLine) {
        if (inLine) value = "INLINE@" + value;
        additionalFields.put(name, value);
        return this;
    }

    @Override
    public void onException(Timer t, Throwable e) {
        if (alreadySent.contains(e.getLocalizedMessage())) return;
        alreadySent.add(e.getMessage());
        DiscordLogEmbedBuilder builder = new DiscordLogEmbedBuilder();
        String stackTrace = getStackTraceAsString(e);
        String consoleLog = "NEW ERROR HAS BEEN THROWED!\nMessage: " + e.getMessage() + "\nLocalized Message: " + e.getLocalizedMessage();
        if (isStackTracesEnabled()) consoleLog += "\nSTACK TRACE:" + stackTrace;
        System.out.println(consoleLog);
        builder.title("New exception!")
                .addField("Message", e.getMessage())
                .addField("Localized Message", e.getLocalizedMessage());
        for (String fieldName : additionalFields.keySet()) {
            String value = additionalFields.get(fieldName);
            boolean inLine = value.startsWith("INLINE@");
            builder.addField(fieldName, value, inLine);
        }
        builder.addField("StackTrace", "**``" + stackTrace + "``**", false);
        builder.send("discord-logs:exception", RedisConnection.mainConnection.getResource());
    }
}
