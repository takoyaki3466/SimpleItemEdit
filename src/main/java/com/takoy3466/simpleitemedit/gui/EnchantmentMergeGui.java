package com.takoy3466.simpleitemedit.gui;

import com.takoy3466.simpleitemedit.context.SimpleItemEditContext;
import com.takoy3466.simpleitemedit.holder.EnchantmentMergeGuiHolder;
import com.takoy3466.simpleitemedit.session.EditingSession;
import com.takoy3466.simpleitemedit.util.GuiUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class EnchantmentMergeGui extends AbstractGui {
    public EnchantmentMergeGui(SimpleItemEditContext context, EditingSession session) {
        super(context, session);
    }

    @Override
    public void open(Player player) {
        Inventory inv = GuiUtil.holderSet(new EnchantmentMergeGuiHolder(session, context.editors()), 54, "Combine Enchantment Books");
        setup(inv);
        player.openInventory(inv);
    }

    @Override
    protected void setup(Inventory inv) {
        inv.setItem(EnchantmentMergeGuiHolder.FIRST_BOOK_SLOT, session.enchantmentState().mergeFirst());
        inv.setItem(EnchantmentMergeGuiHolder.RESULT_SLOT, session.enchantmentState().mergeResult());
        inv.setItem(EnchantmentMergeGuiHolder.SECOND_BOOK_SLOT, session.enchantmentState().mergeSecond());

        setGlass(inv, 28, 30);
        setGlass(inv, 32, 34);

        inv.setItem(EnchantmentMergeGuiHolder.MERGE_BUTTON_SLOT, GuiUtil.button(Material.ANVIL, "Combine Books"));
        inv.setItem(EnchantmentMergeGuiHolder.BACK_SLOT, GuiUtil.button(Material.ARROW, "Back"));
    }

    private void setGlass(Inventory inventory, int start, int end) {
        ItemStack glass = GuiUtil.button(Material.RED_STAINED_GLASS_PANE, " ");

        for (int slot = start; slot <= end; slot++) {

            if (slot == EnchantmentMergeGuiHolder.FIRST_BOOK_SLOT || slot == EnchantmentMergeGuiHolder.SECOND_BOOK_SLOT) {
                continue;
            }

            inventory.setItem(slot, glass);
        }
    }
}