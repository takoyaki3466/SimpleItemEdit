package com.takoy3466.simpleitemedit.util;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public final class EnchantBookMergeUtil {

    private EnchantBookMergeUtil() {
    }

    public static boolean canMerge(ItemStack first, ItemStack second) {
        if (EnchantBookUtil.isNotEnchantedBook(first) || EnchantBookUtil.isNotEnchantedBook(second)) {
            return false;
        }

        if (EnchantBookUtil.hasNotSingleEnchantment(first) || EnchantBookUtil.hasNotSingleEnchantment(second)) {
            return false;
        }

        Enchantment firstEnchantment = EnchantBookUtil.getSingleEnchantment(first);
        Enchantment secondEnchantment = EnchantBookUtil.getSingleEnchantment(second);

        if (!firstEnchantment.equals(secondEnchantment)) {
            return false;
        }

        int firstLevel = EnchantBookUtil.getSingleLevel(first);
        int secondLevel = EnchantBookUtil.getSingleLevel(second);

        if (firstLevel != secondLevel) {
            return false;
        }

        return firstLevel < Integer.MAX_VALUE;
    }

    public static ItemStack merge(ItemStack first, ItemStack second) {
        if (!canMerge(first, second)) {
            return null;
        }

        Enchantment enchantment = EnchantBookUtil.getSingleEnchantment(first);
        int level = EnchantBookUtil.getSingleLevel(first);

        return EnchantBookUtil.createBook(enchantment, level + 1);
    }
}