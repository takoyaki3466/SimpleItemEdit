package com.takoy3466.simpleitemedit.util;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public final class EnchantmentUtil {

    private EnchantmentUtil() {
    }

    public static int getLevel(ItemStack item, Enchantment enchantment) {
        if (item == null || enchantment == null) {
            return 0;
        }

        return item.getEnchantmentLevel(enchantment);
    }

    public static int calculateNewLevel(int currentLevel, int bookLevel) {
        if (bookLevel <= 0) {
            return currentLevel;
        }

        if (currentLevel <= 0) {
            return bookLevel;
        }

        if (bookLevel > currentLevel) {
            return bookLevel;
        }

        if (bookLevel == currentLevel) {
            if (currentLevel == Integer.MAX_VALUE) {
                return Integer.MAX_VALUE;
            }

            return currentLevel + 1;
        }

        return currentLevel;
    }

    public static boolean canApply(int currentLevel, int bookLevel) {
        if (bookLevel <= 0) {
            return false;
        }

        if (currentLevel == Integer.MAX_VALUE) {
            return false;
        }

        return currentLevel == 0 || bookLevel >= currentLevel;
    }

    public static void apply(ItemStack item, Enchantment enchantment, int level) {
        if (item == null || enchantment == null || level <= 0) {
            return;
        }

        item.addUnsafeEnchantment(enchantment, level);
    }

    public static void remove(ItemStack item, Enchantment enchantment) {
        if (item == null || enchantment == null) {
            return;
        }

        item.removeEnchantment(enchantment);
    }
}