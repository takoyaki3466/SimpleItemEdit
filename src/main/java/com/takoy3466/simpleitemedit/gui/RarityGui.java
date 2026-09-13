package com.takoy3466.simpleitemedit.gui;

import com.takoy3466.simpleitemedit.context.SimpleItemEditContext;
import com.takoy3466.simpleitemedit.holder.RarityGuiHolder;
import com.takoy3466.simpleitemedit.session.EditingSession;
import com.takoy3466.simpleitemedit.util.GuiUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class RarityGui extends AbstractGui {
    public RarityGui(SimpleItemEditContext context, EditingSession session) {
        super(context, session);
    }

    @Override
    public void open(Player player) {
        Inventory inv = GuiUtil.holderSet(new RarityGuiHolder(session, context.editors()), 27, "Edit Rarity");
        setup(inv);
        player.openInventory(inv);
    }

    @Override
    protected void setup(Inventory inventory) {
        inventory.setItem(13, session.editingItem());
        inventory.setItem(10, GuiUtil.button(Material.WHITE_WOOL, "Common"));
        inventory.setItem(11, GuiUtil.button(Material.IRON_INGOT, "Uncommon"));
        inventory.setItem(15, GuiUtil.button(Material.DIAMOND, "Rare"));
        inventory.setItem(16, GuiUtil.button(Material.NETHER_STAR, "Epic"));
        inventory.setItem(22, GuiUtil.button(Material.ARROW, "Back"));
    }
}