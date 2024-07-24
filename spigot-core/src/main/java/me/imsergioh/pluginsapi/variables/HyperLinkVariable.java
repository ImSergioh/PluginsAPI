package me.imsergioh.pluginsapi.variables;

import me.imsergioh.pluginsapi.instance.IObjectVariableListener;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.entity.Player;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HyperLinkVariable implements IObjectVariableListener<TextComponent, Player> {

    private static final String REGEX = "\\[(.*?)\\]\\((.*?)\\)";
    private static final Pattern pattern = Pattern.compile(REGEX);

    @Override
    public TextComponent parse(String message) {
        Matcher matcher = pattern.matcher(message);
        TextComponent.Builder textComponentBuilder = Component.text();

        int lastEnd = 0;
        boolean found = false;

        while (matcher.find()) {
            found = true;
            // Añadir el texto entre los enlaces
            if (matcher.start() > lastEnd) {
                textComponentBuilder.append(Component.text(message.substring(lastEnd, matcher.start())));
            }

            String linkText = matcher.group(1);
            String url = matcher.group(2);

            // Crear el componente de texto para el enlace
            TextComponent linkComponent = Component.text(linkText)
                    .clickEvent(ClickEvent.openUrl(url));

            textComponentBuilder.append(linkComponent);

            lastEnd = matcher.end();
        }

        // Si no se encontraron enlaces, retornar null
        if (!found) {
            return null;
        }

        // Añadir cualquier texto restante después del último enlace
        if (lastEnd < message.length()) {
            textComponentBuilder.append(Component.text(message.substring(lastEnd)));
        }
        return textComponentBuilder.build();
    }

    @Override
    public TextComponent parse(Player player, String message) {
        return parse(message);
    }
}
