package com.takoy3466.simpleitemedit.editor;

import com.takoy3466.simpleitemedit.context.IEditContext;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EnchantmentMergeEditor implements IItemEditor {

    @Override
    public String id() {
        return "enchantment_merge";
    }

    @Override
    public Component displayName() {
        return Component.text("Combine Enchantment Books");
    }

    @Override
    public ItemStack createIcon() {

        ItemStack item = new ItemStack(Material.BOOK);

        ItemMeta meta = item.getItemMeta();

        meta.displayName(Component.text("Combine Enchantment Books"));

        item.setItemMeta(meta);

        return item;
    }

    @Override
    public void reset(IEditContext context) {

    }
}