package me.imsergioh.pluginsapi.instance;

import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;


public class TextComponentParser extends ObjectVariableParser<TextComponent> {

    public TextComponentParser() {
        super(TextComponent.class);
    }

    @Override
    public String toString(net.kyori.adventure.text.TextComponent o) {
        System.out.println("TEXTCOMPONENT PARSER -> " + "<<<TEXTCOMPONENT@" + LegacyComponentSerializer.legacyAmpersand().serialize(o) + ">>>");
        return "<<<TEXTCOMPONENT@" + LegacyComponentSerializer.legacyAmpersand().serialize(o) + ">>>";
    }
}
