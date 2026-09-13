package com.takoy3466.simpleitemedit.editor;

import com.takoy3466.simpleitemedit.context.IEditContext;
import com.takoy3466.simpleitemedit.util.EnchantmentUtil;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class EnchantmentApplyEditor extends AbstractItemEditor {
    public EnchantmentApplyEditor() {
        super("enchantment_apply", Material.ENCHANTED_BOOK, "Apply Enchantment");
    }

    @Override
    public void reset(IEditContext context) {
        ItemStack original = context.session().originalItem();
        ItemStack editing = context.session().editingItem();

        for (Enchantment enchantment : editing.getEnchantments().keySet()) {
            editing.removeEnchantment(enchantment);
        }

        for (Map.Entry<Enchantment, Integer> entry : original.getEnchantments().entrySet()) {
            EnchantmentUtil.apply(editing, entry.getKey(), entry.getValue());
        }

        context.session().setEditingItem(editing);
    }
}