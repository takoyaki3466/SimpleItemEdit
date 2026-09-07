package com.takoy3466.simpleitemedit.util;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.util.Map;

public final class EnchantBookUtil {

    private EnchantBookUtil() {
    }

    public static boolean isNotEnchantedBook(ItemStack item) {
        return item == null || item.getType() != Material.ENCHANTED_BOOK || !(item.getItemMeta() instanceof EnchantmentStorageMeta);
    }

    public static Map<Enchantment, Integer> getEnchantments(ItemStack book) {
        if (isNotEnchantedBook(book)) {
            return Map.of();
        }

        EnchantmentStorageMeta meta = (EnchantmentStorageMeta) book.getItemMeta();

        return meta.getStoredEnchants();
    }

    public static boolean hasNotSingleEnchantment(ItemStack book) {
        return getEnchantments(book).size() != 1;
    }

    public static Enchantment getSingleEnchantment(ItemStack book) {
        Map<Enchantment, Integer> enchantments = getEnchantments(book);

        if (enchantments.size() != 1) {
            return null;
        }

        return enchantments.keySet().iterator().next();
    }

    public static int getSingleLevel(ItemStack book) {
        Map<Enchantment, Integer> enchantments = getEnchantments(book);

        if (enchantments.size() != 1) {
            return 0;
        }

        return enchantments.values().iterator().next();
    }

    public static ItemStack createBook(Enchantment enchantment, int level) {
        if (enchantment == null || level <= 0) {
            return null;
        }

        ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);

        EnchantmentStorageMeta meta = (EnchantmentStorageMeta) book.getItemMeta();

        meta.addStoredEnchant(enchantment, level, true);

        book.setItemMeta(meta);

        return book;
    }

    /**
     * ItemStackから1個だけ消費します。
     */
    public static void consumeOne(ItemStack item) {
        if (item == null || item.getType() == Material.AIR) {
            return;
        }

        if (item.getAmount() <= 1) {
            item.setAmount(0);
            return;
        }

        item.setAmount(item.getAmount() - 1);
    }
}