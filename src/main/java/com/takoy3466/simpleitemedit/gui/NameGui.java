package com.takoy3466.simpleitemedit.gui;

import com.takoy3466.simpleitemedit.context.SimpleItemEditContext;
import com.takoy3466.simpleitemedit.holder.NameGuiHolder;
import com.takoy3466.simpleitemedit.session.EditingSession;
import com.takoy3466.simpleitemedit.util.GuiUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class NameGui extends AbstractGui {
    public NameGui(SimpleItemEditContext context, EditingSession session) {
        super(context, session);
    }

    @Override
    public void open(Player player) {
        Inventory inv = GuiUtil.holderSet(new NameGuiHolder(session, context.editors()), 27, "Edit Name");
        setup(inv);
        player.openInventory(inv);
    }

    @Override
    protected void setup(Inventory inventory) {
        inventory.setItem(13, session.editingItem());
        inventory.setItem(11, GuiUtil.button(Material.WRITABLE_BOOK, "Change Name"));
        inventory.setItem(15, GuiUtil.button(Material.BARRIER, "Reset"));
        inventory.setItem(22, GuiUtil.button(Material.ARROW, "Back"));
    }
}