package com.takoy3466.simpleitemedit.util;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class EnchantBookSplitUtil {
    private EnchantBookSplitUtil() {
    }

    public static List<ItemStack> split(ItemStack book) {
        List<ItemStack> result = new ArrayList<>();

        if (!isValidBook(book)) {
            return result;
        }

        EnchantmentStorageMeta meta = (EnchantmentStorageMeta) book.getItemMeta();

        for (Map.Entry<Enchantment, Integer> entry : meta.getStoredEnchants().entrySet()) {
            ItemStack splitBook = new ItemStack(book.getType());

            splitBook.setItemMeta(book.getItemMeta());
            EnchantmentStorageMeta splitMeta = (EnchantmentStorageMeta) splitBook.getItemMeta();

            for (Enchantment enchantment : splitMeta.getStoredEnchants().keySet()) {
                splitMeta.removeStoredEnchant(enchantment);
            }

            splitMeta.addStoredEnchant(entry.getKey(), entry.getValue(), true);
            splitBook.setItemMeta(splitMeta);

            splitBook.setAmount(1);
            result.add(splitBook);
        }

        return result;
    }

    public static boolean isValidBook(ItemStack book) {
        if (book == null || book.getType().isAir()) {
            return false;
        }

        if (EnchantBookUtil.isNotEnchantedBook(book)) {
            return false;
        }

        if (!(book.getItemMeta() instanceof EnchantmentStorageMeta meta)) {
            return false;
        }

        return meta.getStoredEnchants().size() >= 2;
    }

    public static int getEnchantmentCount(ItemStack book) {
        if (EnchantBookUtil.isNotEnchantedBook(book)) {
            return 0;
        }

        if (!(book.getItemMeta() instanceof EnchantmentStorageMeta meta)) {
            return 0;
        }

        return meta.getStoredEnchants().size();
    }
}