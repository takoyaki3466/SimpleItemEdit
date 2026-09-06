package com.takoy3466.simpleitemedit.editor;

import com.takoy3466.simpleitemedit.context.IEditContext;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class NameEditor implements IItemEditor {

    @Override
    public String id() {
        return "name";
    }

    @Override
    public Component displayName() {
        return Component.text("Name");
    }

    @Override
    public ItemStack createIcon() {
        ItemStack item = new ItemStack(Material.NAME_TAG);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text("Name"));
        item.setItemMeta(meta);

        return item;
    }

    @Override
    public void reset(IEditContext context) {
        context.session().setEditingItem(context.session().originalItem());
        context.refresh();
    }

    public void setName(IEditContext context, String name) {
        ItemStack item = context.session().editingItem();
        ItemMeta meta = item.getItemMeta();

        if (meta == null) {
            return;
        }

        meta.displayName(Component.text(name));
        item.setItemMeta(meta);
        context.session().setEditingItem(item);
    }
}