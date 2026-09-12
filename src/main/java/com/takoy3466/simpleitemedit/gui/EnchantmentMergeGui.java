package com.takoy3466.simpleitemedit.gui;

import com.takoy3466.simpleitemedit.context.SimpleItemEditContext;
import com.takoy3466.simpleitemedit.holder.EnchantmentMergeGuiHolder;
import com.takoy3466.simpleitemedit.session.EditingSession;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EnchantmentMergeGui implements IGui {

    private final SimpleItemEditContext context;
    private final EditingSession session;

    public EnchantmentMergeGui(SimpleItemEditContext context, EditingSession session) {
        this.context = context;
        this.session = session;
    }

    @Override
    public void open(Player player) {
        EnchantmentMergeGuiHolder holder = new EnchantmentMergeGuiHolder(session, context.editors());
        Inventory inventory = Bukkit.createInventory(holder, 54, Component.text("Combine Enchantment Books"));
        holder.setInventory(inventory);
        setup(inventory);
        player.openInventory(inventory);
    }

    private void setup(Inventory inventory) {
        inventory.setItem(EnchantmentMergeGuiHolder.FIRST_BOOK_SLOT, session.enchantmentState().mergeFirst());
        inventory.setItem(EnchantmentMergeGuiHolder.RESULT_SLOT, session.enchantmentState().mergeResult());
        inventory.setItem(EnchantmentMergeGuiHolder.SECOND_BOOK_SLOT, session.enchantmentState().mergeSecond());

        setGlass(inventory, 28, 30);
        setGlass(inventory, 32, 34);

        inventory.setItem(EnchantmentMergeGuiHolder.MERGE_BUTTON_SLOT, button(Material.ANVIL, "Combine Books"));
        inventory.setItem(EnchantmentMergeGuiHolder.BACK_SLOT, button(Material.ARROW, "Back"));
    }

    private void setGlass(Inventory inventory, int start, int end) {
        ItemStack glass = button(Material.GRAY_STAINED_GLASS_PANE, " ");

        for (int slot = start; slot <= end; slot++) {

            if (slot == EnchantmentMergeGuiHolder.FIRST_BOOK_SLOT || slot == EnchantmentMergeGuiHolder.SECOND_BOOK_SLOT) {
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