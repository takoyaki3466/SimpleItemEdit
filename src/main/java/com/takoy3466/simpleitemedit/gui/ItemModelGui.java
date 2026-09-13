package com.takoy3466.simpleitemedit.gui;

import com.takoy3466.simpleitemedit.context.SimpleItemEditContext;
import com.takoy3466.simpleitemedit.holder.ItemModelGuiHolder;
import com.takoy3466.simpleitemedit.session.EditingSession;
import com.takoy3466.simpleitemedit.util.GuiUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ItemModelGui extends AbstractGui {
    public ItemModelGui(SimpleItemEditContext context, EditingSession session) {
        super(context, session);
    }

    @Override
    public void open(Player player) {
        Inventory inv = GuiUtil.holderSet(new ItemModelGuiHolder(session, context.editors()), 27, "Edit Item Model");
        setup(inv);
        player.openInventory(inv);
    }

    @Override
    protected void setup(Inventory inventory) {
        inventory.setItem(13, session.editingItem());
        
        inventory.setItem(11, GuiUtil.button(Material.NAME_TAG, "Change Item Model"));

        inventory.setItem(15, GuiUtil.button(Material.BARRIER, "Reset"));
        inventory.setItem(22, GuiUtil.button(Material.ARROW, "Back"));
    }
}