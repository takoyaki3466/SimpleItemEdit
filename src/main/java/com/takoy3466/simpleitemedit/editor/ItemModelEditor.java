package com.takoy3466.simpleitemedit.editor;

import com.takoy3466.simpleitemedit.context.IEditContext;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemModelEditor extends AbstractItemEditor {
    public ItemModelEditor() {
        super("item_model", Material.ITEM_FRAME, "Item Model");
    }

    public void setItemModel(IEditContext context, NamespacedKey itemModel) {
        ItemStack item = context.session().editingItem();
        ItemMeta meta = item.getItemMeta();

        if (meta == null) {
            return;
        }

        meta.setItemModel(itemModel);
        item.setItemMeta(meta);

        context.session().setEditingItem(item);
    }

    public NamespacedKey getItemModel(IEditContext context) {
        ItemStack item = context.session().editingItem();
        ItemMeta meta = item.getItemMeta();

        if (meta == null || !meta.hasItemModel()) {
            return null;
        }

        return meta.getItemModel();
    }

    @Override
    public void reset(IEditContext context) {
        ItemStack original = context.session().originalItem();
        ItemStack item = context.session().editingItem();

        ItemMeta originalMeta = original.getItemMeta();
        ItemMeta meta = item.getItemMeta();

        if (meta == null) {
            return;
        }

        if (originalMeta != null && originalMeta.hasItemModel()) {
            meta.setItemModel(originalMeta.getItemModel());
        } else {
            meta.setItemModel(null);
        }

        item.setItemMeta(meta);

        context.session().setEditingItem(item);
        context.refresh();
    }
}