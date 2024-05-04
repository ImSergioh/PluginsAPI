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
    testMessageList,

    @DefaultLanguageItem(
            material = "BIRCH_PLANKS",
            name = "&bTest Item LES GO",
            description = {"&9Linea1",
                    "Linea2",
                    "&e&lLINEA 3"})
    @ItemMeta(key = "skullTexture", value = "sdfpijfp`jasdfopidfsjdosiajfdowijfoipjaoipsfjiodasjoipfasiofjoijaopjoisdjfjpfodisajopfjadosf")
    testItem;



    @Override
    public String getFieldName() {
        return name();
    }
}
