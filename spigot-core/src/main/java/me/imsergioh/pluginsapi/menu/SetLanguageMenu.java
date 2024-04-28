package me.imsergioh.pluginsapi.menu;

import me.imsergioh.pluginsapi.instance.menu.CoreMenu;
import me.imsergioh.pluginsapi.instance.PlayerLanguages;
import me.imsergioh.pluginsapi.language.Language;
import me.imsergioh.pluginsapi.instance.item.ItemBuilder;
import me.imsergioh.pluginsapi.message.LanguageMessages;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SetLanguageMenu extends CoreMenu {

    private ItemStack ES_Item;
    private ItemStack EN_Item;

    public SetLanguageMenu(Player player) {
        super(player, 45, title(player));
    }

    @Override
    public void load() {
        set(8, getCloseMenuItem(initPlayer), "closeInv");
        set(21, getES_Item(initPlayer), "cmd lang ES", "closeInv");
        set(23, getEN_Item(initPlayer), "cmd lang EN", "closeInv");
        set(40, getPreviousMenuItem(initPlayer), "openPrevious");
    }

    private ItemStack getLangItem(Player player, Language language, String skulltexture) {
        String name = LanguageMessages.get().get(player, PlayerLanguages.get(player.getUniqueId()), "lang." + language.name() + ".name");
        ItemStack item = ItemBuilder.of(Material.PLAYER_HEAD)
                .data(3)
                .name(name)
                .skullTexture(skulltexture)
                .get(player);
        return item;
    }

    private static String title(Player player) {
        return LanguageMessages.get().get(player, PlayerLanguages.get(player.getUniqueId()), "setLangTitle");
    }

    public ItemStack getEN_Item(Player player) {
        if (EN_Item == null) {
            EN_Item = getLangItem(player, Language.EN, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGNhYzk3NzRkYTEyMTcyNDg1MzJjZTE0N2Y3ODMxZjY3YTEyZmRjY2ExY2YwY2I0YjM4NDhkZTZiYzk0YjQifX19");
        }
        return EN_Item;
    }

    public ItemStack getES_Item(Player player) {
        if (ES_Item == null) {
            ES_Item = getLangItem(player, Language.ES, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzJiZDQ1MjE5ODMzMDllMGFkNzZjMWVlMjk4NzQyODc5NTdlYzNkOTZmOGQ4ODkzMjRkYThjODg3ZTQ4NWVhOCJ9fX0=");
        }
        return ES_Item;
    }
}
