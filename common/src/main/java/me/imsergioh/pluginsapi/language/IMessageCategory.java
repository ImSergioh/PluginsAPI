package me.imsergioh.pluginsapi.language;

import me.imsergioh.pluginsapi.handler.LanguagesHandler;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public interface IMessageCategory {

    default String getMessageOf(Language language) {
        String holderName = getClass().getDeclaredAnnotation(LangMessagesInfo.class).name();
        return LanguagesHandler.get(language).get(holderName).getString(getLanguageKeyByFieldName(getFieldName()));
    }

    static Map<String, String> getMessagesMap(Class<? extends IMessageCategory> clazz) {
        Map<String, String> fields = new HashMap<>();
        for (Field field : clazz.getDeclaredFields()) {
            DefaultLanguageMessage defaultLanguageMessage = field.getDeclaredAnnotation(DefaultLanguageMessage.class);
            if (defaultLanguageMessage == null) continue;
            String key = getLanguageKeyByFieldName(field.getName());
            String message = defaultLanguageMessage.value();
            fields.put(key, message);
        }
        return fields;
    }

    static String getLanguageKeyByFieldName(String name) {
        return name.replace("_", ".");
    }

    String getFieldName();

}
