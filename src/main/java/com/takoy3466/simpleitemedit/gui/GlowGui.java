package com.takoy3466.simpleitemedit.gui;

import com.takoy3466.simpleitemedit.context.SimpleItemEditContext;
import com.takoy3466.simpleitemedit.holder.GlowGuiHolder;
import com.takoy3466.simpleitemedit.session.EditingSession;
import com.takoy3466.simpleitemedit.util.GuiUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class GlowGui extends AbstractGui {
    public GlowGui(SimpleItemEditContext context, EditingSession session) {
        super(context, session);
    }

    @Override
    public void open(Player player) {
        GlowGuiHolder holder = new GlowGuiHolder(session, context.editors());
        Inventory inventory = Bukkit.createInventory(holder, 27, Component.text("Edit Glow"));
        holder.setInventory(inventory);
        setup(inventory);
        player.openInventory(inventory);
    }

    @Override
    protected void setup(Inventory inventory) {
        inventory.setItem(13, session.editingItem());
        inventory.setItem(11, GuiUtil.button(Material.ENCHANTED_BOOK, "Enable Glow"));
        inventory.setItem(15, GuiUtil.button(Material.BOOK, "Disable Glow"));
        inventory.setItem(22, GuiUtil.button(Material.ARROW, "Back"));
    }
}