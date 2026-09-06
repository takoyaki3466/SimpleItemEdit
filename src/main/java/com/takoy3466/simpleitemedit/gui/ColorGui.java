package com.takoy3466.simpleitemedit.gui;

import com.takoy3466.simpleitemedit.context.SimpleItemEditContext;
import com.takoy3466.simpleitemedit.holder.ColorGuiHolder;
import com.takoy3466.simpleitemedit.session.EditingSession;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ColorGui implements IGui {

    private final SimpleItemEditContext context;
    private final EditingSession session;

    public ColorGui(SimpleItemEditContext context, EditingSession session) {
        this.context = context;
        this.session = session;
    }

    @Override
    public void open(Player player) {
        ColorGuiHolder holder = new ColorGuiHolder(session, context.editors());
        Inventory inventory = Bukkit.createInventory(holder, 54, Component.text("Edit Color"));
        holder.setInventory(inventory);
        setup(inventory);
        player.openInventory(inventory);
    }

    private void setup(Inventory inventory) {
        inventory.setItem(13, session.editingItem());

        setColor(inventory, 27, "White", NamedTextColor.WHITE, Material.WHITE_WOOL);
        setColor(inventory, 28, "Gray", NamedTextColor.GRAY, Material.GRAY_WOOL);
        setColor(inventory, 29, "Dark Gray", NamedTextColor.DARK_GRAY, Material.DEEPSLATE);
        setColor(inventory, 30, "Black", NamedTextColor.BLACK, Material.BLACK_WOOL);
        setColor(inventory, 31, "Red", NamedTextColor.RED, Material.RED_WOOL);
        setColor(inventory, 32, "Dark Red", NamedTextColor.DARK_RED, Material.NETHER_BRICKS);
        setColor(inventory, 33, "Gold", NamedTextColor.GOLD, Material.GOLD_BLOCK);
        setColor(inventory, 34, "Yellow", NamedTextColor.YELLOW, Material.YELLOW_WOOL);
        setColor(inventory, 35, "Green", NamedTextColor.GREEN, Material.GREEN_WOOL);
        setColor(inventory, 36, "Dark Green", NamedTextColor.DARK_GREEN, Material.DARK_PRISMARINE);
        setColor(inventory, 37, "Aqua", NamedTextColor.AQUA, Material.LIGHT_BLUE_WOOL);
        setColor(inventory, 38, "Dark Aqua", NamedTextColor.DARK_AQUA, Material.DIAMOND_BLOCK);
        setColor(inventory, 39, "Blue", NamedTextColor.BLUE, Material.BLUE_WOOL);
        setColor(inventory, 40, "Dark Blue", NamedTextColor.DARK_BLUE, Material.BLUE_CONCRETE);
        setColor(inventory, 41, "Light Purple", NamedTextColor.LIGHT_PURPLE, Material.PURPLE_WOOL);
        setColor(inventory, 42, "Dark Purple", NamedTextColor.DARK_PURPLE, Material.PURPLE_CONCRETE);

        inventory.setItem(49, button(Material.BARRIER, "Reset"));
        inventory.setItem(53, button(Material.ARROW, "Back"));
    }

    private void setColor(Inventory inventory, int slot, String name, NamedTextColor color, Material blockMaterial) {
        ItemStack item = new ItemStack(blockMaterial);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(Component.text(name, color));
        item.setItemMeta(meta);
        inventory.setItem(slot, item);
    }

    private ItemStack button(Material material, String name) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(Component.text(name));
        item.setItemMeta(meta);

        return item;
    }
}