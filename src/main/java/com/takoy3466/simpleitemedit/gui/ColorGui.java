package com.takoy3466.simpleitemedit.gui;

import com.takoy3466.simpleitemedit.context.SimpleItemEditContext;
import com.takoy3466.simpleitemedit.holder.ColorGuiHolder;
import com.takoy3466.simpleitemedit.session.EditingSession;
import com.takoy3466.simpleitemedit.util.GuiUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ColorGui extends AbstractGui {
    public ColorGui(SimpleItemEditContext context, EditingSession session) {
        super(context, session);
    }

    @Override
    public void open(Player player) {
        Inventory inv = GuiUtil.holderSet(new ColorGuiHolder(session, context.editors()), 54, "Edit Color");
        setup(inv);
        player.openInventory(inv);
    }

    @Override
    protected void setup(Inventory inv) {
        inv.setItem(13, session.editingItem());

        setColor(inv, 27, "White", NamedTextColor.WHITE, Material.WHITE_WOOL);
        setColor(inv, 28, "Gray", NamedTextColor.GRAY, Material.GRAY_WOOL);
        setColor(inv, 29, "Dark Gray", NamedTextColor.DARK_GRAY, Material.DEEPSLATE);
        setColor(inv, 30, "Black", NamedTextColor.BLACK, Material.BLACK_WOOL);
        setColor(inv, 31, "Red", NamedTextColor.RED, Material.RED_WOOL);
        setColor(inv, 32, "Dark Red", NamedTextColor.DARK_RED, Material.NETHER_BRICKS);
        setColor(inv, 33, "Gold", NamedTextColor.GOLD, Material.GOLD_BLOCK);
        setColor(inv, 34, "Yellow", NamedTextColor.YELLOW, Material.YELLOW_WOOL);
        setColor(inv, 35, "Green", NamedTextColor.GREEN, Material.GREEN_WOOL);
        setColor(inv, 36, "Dark Green", NamedTextColor.DARK_GREEN, Material.DARK_PRISMARINE);
        setColor(inv, 37, "Aqua", NamedTextColor.AQUA, Material.LIGHT_BLUE_WOOL);
        setColor(inv, 38, "Dark Aqua", NamedTextColor.DARK_AQUA, Material.DIAMOND_BLOCK);
        setColor(inv, 39, "Blue", NamedTextColor.BLUE, Material.BLUE_WOOL);
        setColor(inv, 40, "Dark Blue", NamedTextColor.DARK_BLUE, Material.BLUE_CONCRETE);
        setColor(inv, 41, "Light Purple", NamedTextColor.LIGHT_PURPLE, Material.PURPLE_WOOL);
        setColor(inv, 42, "Dark Purple", NamedTextColor.DARK_PURPLE, Material.PURPLE_CONCRETE);

        inv.setItem(49, GuiUtil.button(Material.BARRIER, "Reset"));
        inv.setItem(53, GuiUtil.button(Material.ARROW, "Back"));
    }

    private void setColor(Inventory inventory, int slot, String name, NamedTextColor color, Material blockMaterial) {
        ItemStack item = new ItemStack(blockMaterial);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(Component.text(name, color));
        item.setItemMeta(meta);
        inventory.setItem(slot, item);
    }
}