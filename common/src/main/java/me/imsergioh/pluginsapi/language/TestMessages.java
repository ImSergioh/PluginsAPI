package me.imsergioh.pluginsapi.language;

@LangMessagesInfo(name = "test")
public enum TestMessages implements IMessageCategory {

    @DefaultLanguageMessage("&cYou entered the wrong input!")
    notValid_input,

    @DefaultLanguageMessage("&cYou entered the wrong number!")
    notValid_number;

    @Override
    public String getFieldName() {
        return name();
    }
}
