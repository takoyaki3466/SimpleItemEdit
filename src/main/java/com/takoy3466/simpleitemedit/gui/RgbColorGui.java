package com.takoy3466.simpleitemedit.gui;

import com.takoy3466.simpleitemedit.context.SimpleItemEditContext;
import com.takoy3466.simpleitemedit.holder.RgbColorGuiHolder;
import com.takoy3466.simpleitemedit.session.EditingSession;
import com.takoy3466.simpleitemedit.util.GuiUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class RgbColorGui extends AbstractGui {
    public RgbColorGui(SimpleItemEditContext context, EditingSession session) {
        super(context, session);
    }

    @Override
    public void open(Player player) {
        RgbColorGuiHolder holder = new RgbColorGuiHolder(session, context.editors(), 255, 255, 255);
        Inventory inventory = GuiUtil.holderSet(holder, 54, "RGB Color");
        setup(inventory, holder);
        player.openInventory(inventory);
    }

    private void setup(Inventory inventory, RgbColorGuiHolder holder) {
        inventory.setItem(13, colorPreview(holder));

        inventory.setItem(28, GuiUtil.button(Material.RED_WOOL, "R -10"));
        inventory.setItem(29, GuiUtil.button(Material.RED_DYE, "R -1"));
        inventory.setItem(30, value("Red: " + holder.red()));

        inventory.setItem(32, GuiUtil.button(Material.RED_DYE, "R +1"));
        inventory.setItem(33, GuiUtil.button(Material.RED_WOOL, "R +10"));
        inventory.setItem(37, GuiUtil.button(Material.GREEN_WOOL, "G -10"));
        inventory.setItem(38, GuiUtil.button(Material.LIME_DYE, "G -1"));
        inventory.setItem(39, value("Green: " + holder.green()));

        inventory.setItem(41, GuiUtil.button(Material.LIME_DYE, "G +1"));
        inventory.setItem(42, GuiUtil.button(Material.GREEN_WOOL, "G +10"));
        inventory.setItem(46, GuiUtil.button(Material.BLUE_WOOL, "B -10"));
        inventory.setItem(47, GuiUtil.button(Material.BLUE_DYE, "B -1"));
        inventory.setItem(48, value("Blue: " + holder.blue()));

        inventory.setItem(50, GuiUtil.button(Material.BLUE_DYE, "B +1"));
        inventory.setItem(51, GuiUtil.button(Material.BLUE_WOOL, "B +10"));
        inventory.setItem(53, GuiUtil.button(Material.ARROW, "Back"));
    }

    private ItemStack colorPreview(RgbColorGuiHolder holder) {
        ItemStack item = new ItemStack(Material.LEATHER);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(Component.text("RGB: " + holder.red() + ", " + holder.green() + ", " + holder.blue()).color(TextColor.color(holder.red(), holder.green(), holder.blue())));
        item.setItemMeta(meta);

        return item;
    }

    private ItemStack value(String text) {
        return GuiUtil.button(Material.PAPER, text);
    }
}