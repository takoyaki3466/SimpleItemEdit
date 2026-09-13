package com.takoy3466.simpleitemedit.editor;

import com.takoy3466.simpleitemedit.context.IEditContext;
import com.takoy3466.simpleitemedit.util.EditorUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GlowEditor implements IItemEditor {

    @Override
    public String id() {
        return "glow";
    }

    @Override
    public Component displayName() {
        return Component.text("Glow");
    }

    @Override
    public ItemStack createIcon() {
        return EditorUtil.createIcon(Material.ENCHANTED_BOOK, "Glow");
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

        meta.setEnchantmentGlintOverride(originalMeta.getEnchantmentGlintOverride());
        item.setItemMeta(meta);
        context.session().setEditingItem(item);
        context.refresh();
    }

    public void setGlow(IEditContext context, boolean enabled) {
        ItemStack item = context.session().editingItem();
        ItemMeta meta = item.getItemMeta();

        if (meta == null) {
            return;
        }

        meta.setEnchantmentGlintOverride(enabled);
        item.setItemMeta(meta);
        context.session().setEditingItem(item);
    }
}