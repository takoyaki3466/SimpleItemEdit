package com.takoy3466.simpleitemedit.editor;

import com.takoy3466.simpleitemedit.context.IEditContext;
import com.takoy3466.simpleitemedit.util.EnchantmentUtil;
import com.takoy3466.simpleitemedit.util.EnchantBookUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;

public class EnchantmentApplyEditor implements IItemEditor {

    @Override
    public String id() {
        return "enchantment_apply";
    }

    @Override
    public Component displayName() {
        return Component.text("Enchantment");
    }

    @Override
    public ItemStack createIcon() {
        ItemStack item = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(Component.text("Apply Enchantment"));
        item.setItemMeta(meta);

        return item;
    }

    public boolean applyBook(IEditContext context, ItemStack book) {
        if (EnchantBookUtil.isNotEnchantedBook(book)) {
            return false;
        }

        if (EnchantBookUtil.hasNotSingleEnchantment(book)) {
            return false;
        }

        Enchantment enchantment = EnchantBookUtil.getSingleEnchantment(book);
        int bookLevel = EnchantBookUtil.getSingleLevel(book);
        ItemStack item = context.session().editingItem();

        int currentLevel = EnchantmentUtil.getLevel(item, enchantment);

        if (!EnchantmentUtil.canApply(currentLevel, bookLevel)) {
            return false;
        }

        int newLevel = EnchantmentUtil.calculateNewLevel(currentLevel, bookLevel);
        EnchantmentUtil.apply(item, enchantment, newLevel);

        context.session().setEditingItem(item);

        return true;
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