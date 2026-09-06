package com.takoy3466.simpleitemedit.editor;

import com.takoy3466.simpleitemedit.context.IEditContext;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class StyleEditor implements IItemEditor {

    @Override
    public String id() {
        return "style";
    }

    @Override
    public Component displayName() {
        return Component.text("Style");
    }

    @Override
    public ItemStack createIcon() {
        ItemStack item = new ItemStack(Material.WRITABLE_BOOK);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text("Style"));
        item.setItemMeta(meta);

        return item;
    }

    public void setStyle(IEditContext context, TextDecoration decoration, boolean enabled) {
        ItemStack item = context.session().editingItem();
        ItemMeta meta = item.getItemMeta();

        if (meta == null) {
            return;
        }

        Component name = meta.displayName();

        if (name == null) {
            name = item.effectiveName();
        }

        name = name.decoration(decoration, enabled ? TextDecoration.State.TRUE : TextDecoration.State.FALSE);
        meta.displayName(name);
        item.setItemMeta(meta);
        context.session().setEditingItem(item);
    }

    public boolean isEnabled(IEditContext context, TextDecoration decoration) {
        ItemStack item = context.session().editingItem();
        ItemMeta meta = item.getItemMeta();

        if (meta == null) {
            return false;
        }

        Component name = meta.displayName();

        if (name == null) {
            return false;
        }

        return name.decoration(decoration) == TextDecoration.State.TRUE;
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

        for (TextDecoration decoration : TextDecoration.values()) {
            currentName = currentName.decoration(decoration, originalName.decoration(decoration));
        }

        meta.displayName(currentName);
        item.setItemMeta(meta);
        context.session().setEditingItem(item);
        context.refresh();
    }
}