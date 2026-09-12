package com.takoy3466.simpleitemedit.gui;

import com.takoy3466.simpleitemedit.context.SimpleItemEditContext;
import com.takoy3466.simpleitemedit.holder.RgbColorGuiHolder;
import com.takoy3466.simpleitemedit.session.EditingSession;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class RgbColorGui implements IGui {

    private final SimpleItemEditContext context;
    private final EditingSession session;

    public RgbColorGui(SimpleItemEditContext context, EditingSession session) {
        this.context = context;
        this.session = session;
    }

    @Override
    public void open(Player player) {
        RgbColorGuiHolder holder = new RgbColorGuiHolder(session, context.editors(), 255, 255, 255);
        Inventory inventory = Bukkit.createInventory(holder, 54, Component.text("RGB Color"));
        holder.setInventory(inventory);
        setup(inventory, holder);
        player.openInventory(inventory);
    }

    private void setup(Inventory inventory, RgbColorGuiHolder holder) {
        inventory.setItem(13, colorPreview(holder));

        inventory.setItem(28, button(Material.RED_WOOL, "R -10"));
        inventory.setItem(29, button(Material.RED_DYE, "R -1"));
        inventory.setItem(30, value("Red: " + holder.red()));

        inventory.setItem(32, button(Material.RED_DYE, "R +1"));
        inventory.setItem(33, button(Material.RED_WOOL, "R +10"));
        inventory.setItem(37, button(Material.GREEN_WOOL, "G -10"));
        inventory.setItem(38, button(Material.LIME_DYE, "G -1"));
        inventory.setItem(39, value("Green: " + holder.green()));

        inventory.setItem(41, button(Material.LIME_DYE, "G +1"));
        inventory.setItem(42, button(Material.GREEN_WOOL, "G +10"));
        inventory.setItem(46, button(Material.BLUE_WOOL, "B -10"));
        inventory.setItem(47, button(Material.BLUE_DYE, "B -1"));
        inventory.setItem(48, value("Blue: " + holder.blue()));

        inventory.setItem(50, button(Material.BLUE_DYE, "B +1"));
        inventory.setItem(51, button(Material.BLUE_WOOL, "B +10"));
        inventory.setItem(53, button(Material.ARROW, "Back"));
    }

    private ItemStack colorPreview(RgbColorGuiHolder holder) {
        ItemStack item = new ItemStack(Material.LEATHER);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(Component.text("RGB: " + holder.red() + ", " + holder.green() + ", " + holder.blue()).color(TextColor.color(holder.red(), holder.green(), holder.blue())));
        item.setItemMeta(meta);

        return item;
    }

    private ItemStack value(String text) {
        return button(Material.PAPER, text);
    }

    private ItemStack button(Material material, String name) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(Component.text(name));
        item.setItemMeta(meta);

        return item;
    }
}