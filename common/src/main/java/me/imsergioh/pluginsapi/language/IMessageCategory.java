package me.imsergioh.pluginsapi.language;

import me.imsergioh.pluginsapi.handler.LanguagesHandler;
import org.bson.Document;

import java.lang.reflect.Field;
import java.util.*;

public interface IMessageCategory {

    default Document getDocumentItem(Language language) {
        return getDocumentOf(language);
    }

    default String getObjectOfToString(Language language) {
        Class<?> type = getClassType(language);
        if (type.equals(String.class)) return getMessageOf(language);
        if (type.equals(ArrayList.class)) return getListFromArray(getMessageListOf(language));
        return "INVALID TYPE@" + getFieldName() + "-" + type.getName();
    }

    default List<String> getMessageListOf(Language language) {
        String holderName = getClass().getDeclaredAnnotation(LangMessagesInfo.class).name();
        return LanguagesHandler.get(language).get(holderName).getList(getLanguageKeyByFieldName(getFieldName()), String.class);
    }

    default String getMessageOf(Language language) {
        String holderName = getClass().getDeclaredAnnotation(LangMessagesInfo.class).name();
        return LanguagesHandler.get(language).get(holderName).getString(getLanguageKeyByFieldName(getFieldName()));
    }

    default Document getDocumentOf(Language language) {
        String holderName = getClass().getDeclaredAnnotation(LangMessagesInfo.class).name();
        return LanguagesHandler.get(language).get(holderName).get(getLanguageKeyByFieldName(getFieldName()), Document.class);
    }

    default Class<?> getClassType(Language language) {
        String holderName = getClass().getDeclaredAnnotation(LangMessagesInfo.class).name();
        return LanguagesHandler.get(language).get(holderName).get(getLanguageKeyByFieldName(getFieldName())).getClass();
    }

    static Set<String> getOldPaths(Class<? extends IMessageCategory> clazz) {
        Set<String> oldPaths = new HashSet<>();
        for (Field field : clazz.getDeclaredFields()) {
            for (RemoveOldPath oldPathAnnotation : field.getDeclaredAnnotationsByType(RemoveOldPath.class)) {
                oldPaths.add(getLanguageKeyByFieldName(oldPathAnnotation.value()));
            }
        }
        return oldPaths;
    }

    static Map<String, Object> getMessagesMap(Class<? extends IMessageCategory> clazz) {
        Map<String, Object> fields = new HashMap<>();

        // LanguageMessage
        for (Field field : clazz.getDeclaredFields()) {
            DefaultLanguageMessage annotation = field.getDeclaredAnnotation(DefaultLanguageMessage.class);
            if (annotation == null) continue;
            String key = getLanguageKeyByFieldName(field.getName());
            String value = annotation.value();
            fields.put(key, value);
        }

        // LanguageMessagesList
        for (Field field : clazz.getDeclaredFields()) {
            DefaultLanguageMessagesList annotation = field.getDeclaredAnnotation(DefaultLanguageMessagesList.class);
            if (annotation == null) continue;
            String key = getLanguageKeyByFieldName(field.getName());
            String[] value = annotation.value();
            fields.put(key, value);
        }

        // LanguageMessageItem
        for (Field field : clazz.getDeclaredFields()) {
            DefaultLanguageItem annotation = field.getDeclaredAnnotation(DefaultLanguageItem.class);
            if (annotation == null) continue;
            String key = getLanguageKeyByFieldName(field.getName());
            Document document = new Document()
                    .append("material", annotation.material())
                    .append("amount", annotation.amount());
            if (annotation.name().length() >= 2) {
                document.append("name", annotation.name());
            }

            if (annotation.description().length >= 1) {
                document.append("description", annotation.description());
            }
            fields.put(key, document);
        }

        // ItemMeta (additional data for items to put in document from item)
        for (Field field : clazz.getDeclaredFields()) {
            ItemMeta annotation = field.getDeclaredAnnotation(ItemMeta.class);
            if (annotation == null) continue;
            String key = getLanguageKeyByFieldName(field.getName());
            Document document = (Document) fields.get(key);
            if (document == null) continue;
            document.put(annotation.key(), annotation.value());
            fields.put(key, document);
        }

        return fields;
    }

    static Map<String, String[]> getMessagesListsMap(Class<? extends IMessageCategory> clazz) {
        Map<String, String[]> fields = new HashMap<>();
        for (Field field : clazz.getDeclaredFields()) {
            DefaultLanguageMessagesList defaultLanguageMessage = field.getDeclaredAnnotation(DefaultLanguageMessagesList.class);
            if (defaultLanguageMessage == null) continue;
            String key = getLanguageKeyByFieldName(field.getName());
            String[] message = defaultLanguageMessage.value();
            fields.put(key, message);
        }
        return fields;
    }

    static String getLanguageKeyByFieldName(String name) {
        return name.replace("_", ".");
    }

    private static String getListFromArray(List<String> list) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String line : list) {
            stringBuilder.append(line).append("\n");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    String getFieldName();

}
