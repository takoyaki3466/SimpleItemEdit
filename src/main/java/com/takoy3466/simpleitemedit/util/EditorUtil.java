package com.takoy3466.simpleitemedit.util;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EditorUtil {
    public static ItemStack createIcon(Material material, Component text) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(text);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createIcon(Material material, String text) {
        return createIcon(material, Component.text(text));
    }
}
