package com.takoy3466.simpleitemedit.gui;

import com.takoy3466.simpleitemedit.context.SimpleItemEditContext;
import com.takoy3466.simpleitemedit.holder.EnchantmentApplyGuiHolder;
import com.takoy3466.simpleitemedit.session.EditingSession;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EnchantmentApplyGui implements IGui {
    private final SimpleItemEditContext context;
    private final EditingSession session;

    public EnchantmentApplyGui(SimpleItemEditContext context, EditingSession session) {
        this.context = context;
        this.session = session;
    }

    @Override
    public void open(Player player) {
        EnchantmentApplyGuiHolder holder = new EnchantmentApplyGuiHolder(session, context.editors());
        Inventory inventory = Bukkit.createInventory(holder, 54, Component.text("Apply Enchantment"));
        holder.setInventory(inventory);
        setup(inventory);
        player.openInventory(inventory);
    }

    private void setup(Inventory inventory) {
        inventory.setItem(EnchantmentApplyGuiHolder.TARGET_SLOT, session.editingItem());
        inventory.setItem(EnchantmentApplyGuiHolder.BOOK_SLOT, session.enchantmentState().applyBook());
        setGlass(inventory, 28, 30);

        inventory.setItem(EnchantmentApplyGuiHolder.APPLY_BUTTON_SLOT, button(Material.ANVIL, "Apply Enchantment"));
        inventory.setItem(EnchantmentApplyGuiHolder.RESET_SLOT, button(Material.RED_DYE, "Reset Enchantments"));
        inventory.setItem(EnchantmentApplyGuiHolder.BACK_SLOT, button(Material.ARROW, "Back"));
    }

    private void setGlass(Inventory inventory, int start, int end) {
        ItemStack glass = button(Material.GRAY_STAINED_GLASS_PANE, " ");

        for (int slot = start; slot <= end; slot++) {
            if (slot == EnchantmentApplyGuiHolder.BOOK_SLOT) {
                continue;
            }

            inventory.setItem(slot, glass);
        }
    }

    private ItemStack button(Material material, String name) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(Component.text(name));
        item.setItemMeta(meta);

        return item;
    }
}