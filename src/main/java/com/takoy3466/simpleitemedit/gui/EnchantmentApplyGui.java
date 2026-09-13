package com.takoy3466.simpleitemedit.gui;

import com.takoy3466.simpleitemedit.context.SimpleItemEditContext;
import com.takoy3466.simpleitemedit.holder.EnchantmentApplyGuiHolder;
import com.takoy3466.simpleitemedit.session.EditingSession;
import com.takoy3466.simpleitemedit.util.GuiUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class EnchantmentApplyGui extends AbstractGui {
    public EnchantmentApplyGui(SimpleItemEditContext context, EditingSession session) {
        super(context, session);
    }

    @Override
    public void open(Player player) {
        Inventory inv = GuiUtil.holderSet(new EnchantmentApplyGuiHolder(session, context.editors()), 54, "Apply Enchantment");
        setup(inv);
        player.openInventory(inv);
    }

    @Override
    protected void setup(Inventory inv) {
        inv.setItem(EnchantmentApplyGuiHolder.TARGET_SLOT, session.editingItem());
        inv.setItem(EnchantmentApplyGuiHolder.BOOK_SLOT, session.enchantmentState().applyBook());
        setGlass(inv, 28, 30);

        inv.setItem(EnchantmentApplyGuiHolder.APPLY_BUTTON_SLOT, GuiUtil.button(Material.ANVIL, "Apply Enchantment"));
        inv.setItem(EnchantmentApplyGuiHolder.RESET_SLOT, GuiUtil.button(Material.RED_DYE, "Reset Enchantments"));
        inv.setItem(EnchantmentApplyGuiHolder.BACK_SLOT, GuiUtil.button(Material.ARROW, "Back"));
    }

    private void setGlass(Inventory inventory, int start, int end) {
        ItemStack glass = GuiUtil.button(Material.RED_STAINED_GLASS_PANE, " ");

        for (int slot = start; slot <= end; slot++) {
            if (slot == EnchantmentApplyGuiHolder.BOOK_SLOT) {
                continue;
            }

            inventory.setItem(slot, glass);
        }
    }
}