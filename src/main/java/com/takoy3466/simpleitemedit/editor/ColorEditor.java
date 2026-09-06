package com.takoy3466.simpleitemedit.editor;

import com.takoy3466.simpleitemedit.context.IEditContext;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ColorEditor implements IItemEditor {

    @Override
    public String id() {
        return "color";
    }

    @Override
    public Component displayName() {
        return Component.text("Color");
    }

    @Override
    public ItemStack createIcon() {
        ItemStack item = new ItemStack(Material.PAINTING);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text("Color"));
        item.setItemMeta(meta);

        return item;
    }

    public void setColor(IEditContext context, TextColor color) {
        ItemStack item = context.session().editingItem();
        ItemMeta meta = item.getItemMeta();

        if (meta == null) {
            return;
        }

        Component name = meta.displayName();

        if (name == null) {
            name = item.effectiveName();
        }

        meta.displayName(name.color(color));
        item.setItemMeta(meta);
        context.session().setEditingItem(item);
    }

    @Override
    public void reset(IEditContext context) {
        ItemStack original = context.session().originalItem();
        ItemStack item = context.session().editingItem();

        ItemMeta originalMeta = original.getItemMeta();
        ItemMeta meta = item.getItemMeta();

        if (originalMeta == null || meta == null) {
            return;
        }

        Component originalName = originalMeta.displayName();

        if (originalName == null) {
            return;
        }

        Component currentName = meta.displayName();

        if (currentName == null) {
            currentName = originalName;
        }

        meta.displayName(currentName.color(originalName.color()));
        item.setItemMeta(meta);
        context.session().setEditingItem(item);
        context.refresh();
    }
}