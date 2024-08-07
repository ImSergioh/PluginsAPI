package me.imsergioh.pluginsapi.language;

import me.imsergioh.pluginsapi.handler.LanguagesHandler;

import java.util.Map;

public class EnumMessagesRegistry {

    @SafeVarargs
    public static void registerLanguageHolder(Class<? extends IMessageCategory>... classes) {
        for (Class<? extends IMessageCategory> clazz : classes) {
            registerMessagesByClass(clazz);
        }
    }

    private static void registerMessagesByClass(Class<? extends IMessageCategory> clazz) {
        LangMessagesInfo langMessagesInfo = clazz.getDeclaredAnnotation(LangMessagesInfo.class);
        Map<String, Object> fields = IMessageCategory.getMessagesMap(clazz);
        for (Language language : Language.values()) {
            LanguageMessagesHolder holder = LanguagesHandler.get(language).get(langMessagesInfo.name());
            // Remove old paths
            IMessageCategory.getOldPaths(clazz).forEach(holder::remove);
            fields.forEach(holder::registerDefault);
            holder.save();
        }
    }

}
