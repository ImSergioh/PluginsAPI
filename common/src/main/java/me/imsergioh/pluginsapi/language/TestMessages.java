package me.imsergioh.pluginsapi.language;

@LangMessagesInfo(name = "test")
public enum TestMessages implements IMessageCategory {

    @DefaultLanguageMessage("&cYou entered the wrong input!")
    @RemoveOldPath("notValid.input")
    notValidInput,

    @DefaultLanguageMessage("&cYou entered the wrong number!")
    notValid_number,

    @DefaultLanguageMessagesList({
            "Linea1",
            "Linea2",
            "no se",
            "33"})
    testMessageList;



    @Override
    public String getFieldName() {
        return name();
    }
}
