package com.takoy3466.simpleitemedit.util;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class EnchantLimitManager {
    private final Plugin plugin;

    private final Map<Enchantment, Integer> limits = new HashMap<>();

    private int unbreakingArmorLimit;
    private int unbreakingCrossbowLimit;
    private int unbreakingTridentLimit;
    private int unbreakingOtherLimit;

    public EnchantLimitManager(Plugin plugin) {
        this.plugin = plugin;
        reload();
    }

    public void reload() {
        limits.clear();

        ConfigurationSection section = plugin.getConfig().getConfigurationSection("enchantment-limits");

        if (section == null) {
            plugin.getLogger().warning("enchantment-limits が config.yml にありません。");
            return;
        }

        load("aqua_affinity", Enchantment.AQUA_AFFINITY, section);
        load("bane_of_arthropods", Enchantment.BANE_OF_ARTHROPODS, section);
        load("blast_protection", Enchantment.BLAST_PROTECTION, section);
        load("breach", Enchantment.BREACH, section);
        load("channeling", Enchantment.CHANNELING, section);
        load("density", Enchantment.DENSITY, section);
        load("depth_strider", Enchantment.DEPTH_STRIDER, section);
        load("efficiency", Enchantment.EFFICIENCY, section);
        load("feather_falling", Enchantment.FEATHER_FALLING, section);
        load("fire_aspect", Enchantment.FIRE_ASPECT, section);
        load("fire_protection", Enchantment.FIRE_PROTECTION, section);
        load("flame", Enchantment.FLAME, section);
        load("fortune", Enchantment.FORTUNE, section);
        load("frost_walker", Enchantment.FROST_WALKER, section);
        load("impaling", Enchantment.IMPALING, section);
        load("infinity", Enchantment.INFINITY, section);
        load("knockback", Enchantment.KNOCKBACK, section);
        load("looting", Enchantment.LOOTING, section);
        load("loyalty", Enchantment.LOYALTY, section);
        load("luck_of_the_sea", Enchantment.LUCK_OF_THE_SEA, section);
        load("lure", Enchantment.LURE, section);
        load("mending", Enchantment.MENDING, section);
        load("multishot", Enchantment.MULTISHOT, section);
        load("piercing", Enchantment.PIERCING, section);
        load("power", Enchantment.POWER, section);
        load("projectile_protection", Enchantment.PROJECTILE_PROTECTION, section);
        load("protection", Enchantment.PROTECTION, section);
        load("punch", Enchantment.PUNCH, section);
        load("quick_charge", Enchantment.QUICK_CHARGE, section);
        load("respiration", Enchantment.RESPIRATION, section);
        load("riptide", Enchantment.RIPTIDE, section);
        load("sharpness", Enchantment.SHARPNESS, section);
        load("silk_touch", Enchantment.SILK_TOUCH, section);
        load("smite", Enchantment.SMITE, section);
        load("soul_speed", Enchantment.SOUL_SPEED, section);
        load("sweeping_edge", Enchantment.SWEEPING_EDGE, section);
        load("swift_sneak", Enchantment.SWIFT_SNEAK, section);
        load("thorns", Enchantment.THORNS, section);
        load("wind_burst", Enchantment.WIND_BURST, section);
        load("lunge", Enchantment.LUNGE, section);

        ConfigurationSection unbreaking = section.getConfigurationSection("unbreaking");

        if (unbreaking != null) {
            unbreakingArmorLimit = unbreaking.getInt("armor", -1);
            unbreakingCrossbowLimit = unbreaking.getInt("crossbow", -1);
            unbreakingTridentLimit = unbreaking.getInt("trident", -1);
            unbreakingOtherLimit = unbreaking.getInt("other", -1);
        }
    }

    private void load(String path, Enchantment enchantment, ConfigurationSection section) {
        if (!section.contains(path)) {
            return;
        }

        limits.put(enchantment, section.getInt(path, -1));
    }

    public int getLimit(ItemStack target, Enchantment enchantment) {
        if (enchantment == Enchantment.UNBREAKING) {
            return getUnbreakingLimit(target);
        }

        return limits.getOrDefault(enchantment, -1);
    }

    private int getUnbreakingLimit(ItemStack item) {
        if (item == null) {
            return unbreakingOtherLimit;
        }

        Material material = item.getType();

        if (isArmor(material)) {
            return unbreakingArmorLimit;
        }

        if (isCrossbow(material)) {
            return unbreakingCrossbowLimit;
        }

        if (isTrident(material)) {
            return unbreakingTridentLimit;
        }

        return unbreakingOtherLimit;
    }

    private boolean isArmor(Material material) {
        String name = material.name();
        return name.endsWith("_HELMET") || name.endsWith("_CHESTPLATE") || name.endsWith("_LEGGINGS") || name.endsWith("_BOOTS") || name.equals("ELYTRA");
    }

    private boolean isCrossbow(Material material) {
        return material == Material.CROSSBOW;
    }

    private boolean isTrident(Material material) {
        return material == Material.TRIDENT;
    }

    public boolean isAllowed(ItemStack target, Enchantment enchantment, int level) {
        if (level <= 0) {
            return false;
        }

        int limit = getLimit(target, enchantment);

        if (limit < 0) {
            return true;
        }

        return level <= limit;
    }

    public int clamp(ItemStack target, Enchantment enchantment, int level) {
        int limit = getLimit(target, enchantment);

        if (limit < 0) {
            return level;
        }

        return Math.min(level, limit);
    }

    public Map<Enchantment, Integer> getLimits() {
        return Collections.unmodifiableMap(limits);
    }

    public int getUnbreakingArmorLimit() {
        return unbreakingArmorLimit;
    }

    public int getUnbreakingCrossbowLimit() {
        return unbreakingCrossbowLimit;
    }

    public int getUnbreakingTridentLimit() {
        return unbreakingTridentLimit;
    }

    public int getUnbreakingOtherLimit() {
        return unbreakingOtherLimit;
    }
}