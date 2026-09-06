package com.takoy3466.simpleitemedit.editor;

import com.takoy3466.simpleitemedit.context.IEditContext;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemModelEditor implements IItemEditor {

    @Override
    public String id() {
        return "item_model";
    }

    @Override
    public Component displayName() {
        return Component.text("Item Model");
    }

    @Override
    public ItemStack createIcon() {
        ItemStack item = new ItemStack(Material.ITEM_FRAME);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(Component.text("Item Model"));
        item.setItemMeta(meta);

        return item;
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