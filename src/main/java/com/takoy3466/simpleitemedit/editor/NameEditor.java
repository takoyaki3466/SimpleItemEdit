package com.takoy3466.simpleitemedit.editor;

import com.takoy3466.simpleitemedit.context.IEditContext;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class NameEditor extends AbstractItemEditor {
    public NameEditor() {
        super("name", Material.NAME_TAG, "Name");
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