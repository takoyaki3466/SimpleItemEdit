package com.takoy3466.simpleitemedit.editor;

import com.takoy3466.simpleitemedit.context.IEditContext;
import org.bukkit.Material;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class RarityEditor extends AbstractItemEditor {
    public RarityEditor() {
        super("rarity", Material.DIAMOND, "Rarity");
    }

    @Override
    public void reset(IEditContext context) {
        ItemStack original = context.session().originalItem();
        ItemStack item = context.session().editingItem();

        ItemMeta originalMeta = original.getItemMeta();
        ItemMeta meta = item.getItemMeta();

        if (originalMeta != null && originalMeta.hasRarity()) {
            meta.setRarity(originalMeta.getRarity());

        } else {
            meta.setRarity(ItemRarity.COMMON);
        }

        item.setItemMeta(meta);

        context.session().setEditingItem(item);

        context.refresh();
    }

    public void setRarity(IEditContext context, ItemRarity rarity) {
        ItemStack item = context.session().editingItem();
        ItemMeta meta = item.getItemMeta();

        if (meta == null) {
            return;
        }

        meta.setRarity(rarity);
        item.setItemMeta(meta);
        context.session().setEditingItem(item);
    }
}