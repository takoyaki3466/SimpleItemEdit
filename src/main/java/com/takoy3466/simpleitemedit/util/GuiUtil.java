package com.takoy3466.simpleitemedit.util;

import com.takoy3466.simpleitemedit.holder.AbstractGuiHolder;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GuiUtil {
    public static <T extends AbstractGuiHolder> Inventory holderSet(T holder, int size, String text) {
        Inventory inventory = Bukkit.createInventory(holder, size, Component.text(text));
        holder.setInventory(inventory);
        return inventory;
    }

    public static ItemStack button(Material material, String name) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(Component.text(name));
        item.setItemMeta(meta);

        return item;
    }
}
