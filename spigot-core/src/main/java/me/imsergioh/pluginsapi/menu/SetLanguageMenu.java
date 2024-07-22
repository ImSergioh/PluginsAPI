package me.imsergioh.pluginsapi.menu;

import me.imsergioh.pluginsapi.instance.PlayerLanguages;
import me.imsergioh.pluginsapi.instance.item.ItemBuilder;
import me.imsergioh.pluginsapi.instance.menu.CoreMenu;
import me.imsergioh.pluginsapi.language.Language;
import me.imsergioh.pluginsapi.message.LanguageMessages;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class SetLanguageMenu extends CoreMenu {

    private static final Map<Language, String> skullTextures = new HashMap<>();

    static {
        skullTextures.put(Language.EN, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGNhYzk3NzRkYTEyMTcyNDg1MzJjZTE0N2Y3ODMxZjY3YTEyZmRjY2ExY2YwY2I0YjM4NDhkZTZiYzk0YjQifX19");
        skullTextures.put(Language.ES, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzJiZDQ1MjE5ODMzMDllMGFkNzZjMWVlMjk4NzQyODc5NTdlYzNkOTZmOGQ4ODkzMjRkYThjODg3ZTQ4NWVhOCJ9fX0=");
    }

    public SetLanguageMenu(Player player) {
        super(player, 45, title(player));
    }

    @Override
    public void load() {
        set(8, getCloseMenuItem(initPlayer), "closeInv");
        set(21, getLangItem(initPlayer, Language.ES).get(initPlayer), "cmd lang ES", "closeInv");
        set(23, getLangItem(initPlayer, Language.EN).get(initPlayer), "cmd lang EN", "closeInv");
        set(40, getPreviousMenuItem(initPlayer), "openPrevious");
    }

    public static ItemBuilder getLangItem(Player player, Language language) {
        String name = LanguageMessages.get().get(player, PlayerLanguages.get(player.getUniqueId()), "lang." + language.name() + ".name");
        String skullTexture = skullTextures.get(language);
        ItemBuilder builder = ItemBuilder.of(Material.PLAYER_HEAD)
                .data(3)
                .name(name);
        if (skullTexture != null) builder.skullTexture(skullTexture);
        return builder;
    }

    private static String title(Player player) {
        return LanguageMessages.get().get(player, PlayerLanguages.get(player.getUniqueId()), "setLangTitle");
    }
}
