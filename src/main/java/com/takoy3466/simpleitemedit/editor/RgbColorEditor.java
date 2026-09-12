package com.takoy3466.simpleitemedit.editor;

import com.takoy3466.simpleitemedit.context.IEditContext;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class RgbColorEditor implements IItemEditor {

    @Override
    public String id() {
        return "rgb_color";
    }

    @Override
    public Component displayName() {
        return Component.text("RGB Color");
    }

    @Override
    public ItemStack createIcon() {
        ItemStack item = new ItemStack(Material.LEATHER);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(Component.text("RGB Color").color(TextColor.color(255, 0, 0)));
        item.setItemMeta(meta);

        return item;
    }

    public void setColor(IEditContext context, int red, int green, int blue) {
        red = clamp(red);
        green = clamp(green);
        blue = clamp(blue);

        ItemStack item = context.session().editingItem();
        ItemMeta meta = item.getItemMeta();

        if (meta == null) {
            return;
        }

        Component name = meta.displayName();

        if (name == null) {
            name = Component.text(item.getType().name());
        }

        meta.displayName(name.color(TextColor.color(red, green, blue)));
        item.setItemMeta(meta);

        context.session().setEditingItem(item);
    }

    private int clamp(int value) {
        return Math.max(0, Math.min(255, value));
    }

    @Override
    public void reset(IEditContext context) {
        ItemStack original = context.session().originalItem();
        ItemStack editing = context.session().editingItem();

        ItemMeta originalMeta = original.getItemMeta();
        ItemMeta editingMeta = editing.getItemMeta();

        if (editingMeta == null) {
            return;
        }

        if (originalMeta != null) {
            editingMeta.displayName(originalMeta.displayName());
        } else {
            editingMeta.displayName(null);
        }

        editing.setItemMeta(editingMeta);
        context.session().setEditingItem(editing);

        context.refresh();
    }
}