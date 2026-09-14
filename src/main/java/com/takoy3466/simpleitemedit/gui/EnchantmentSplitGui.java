package com.takoy3466.simpleitemedit.gui;

import com.takoy3466.simpleitemedit.context.SimpleItemEditContext;
import com.takoy3466.simpleitemedit.holder.EnchantmentSplitGuiHolder;
import com.takoy3466.simpleitemedit.session.EditingSession;
import com.takoy3466.simpleitemedit.util.GuiUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class EnchantmentSplitGui extends AbstractGui {

    public EnchantmentSplitGui(SimpleItemEditContext context, EditingSession session) {
        super(context, session);
    }

    @Override
    public void open(Player player) {
        Inventory inventory = GuiUtil.holderSet(new EnchantmentSplitGuiHolder(session, context.editors()), 54, "エンチャント本の分割");
        setup(inventory);
        player.openInventory(inventory);
    }

    @Override
    protected void setup(Inventory inventory) {
        inventory.setItem(EnchantmentSplitGuiHolder.TARGET_BOOK_SLOT, session.enchantmentState().splitBook());
        int slot = EnchantmentSplitGuiHolder.RESULT_START_SLOT;
        for (ItemStack result : session.enchantmentState().splitResults()) {

            if (slot > EnchantmentSplitGuiHolder.RESULT_END_SLOT) {
                break;
            }

            inventory.setItem(slot, result);

            slot++;
        }

        ItemStack glass = GuiUtil.button(Material.GRAY_STAINED_GLASS_PANE, " ");

        inventory.setItem(0, glass);
        inventory.setItem(2, glass);

        inventory.setItem(EnchantmentSplitGuiHolder.RESET_SLOT, GuiUtil.button(Material.RED_DYE, "リセット"));
        inventory.setItem(EnchantmentSplitGuiHolder.BACK_SLOT, GuiUtil.button(Material.ARROW, "戻る"));
    }
}