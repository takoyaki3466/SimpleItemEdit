package com.takoy3466.simpleitemedit.gui;

import com.takoy3466.simpleitemedit.context.SimpleItemEditContext;
import com.takoy3466.simpleitemedit.holder.EnchantmentGuiHolder;
import com.takoy3466.simpleitemedit.session.EditingSession;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EnchantmentGui implements IGui {

    private final SimpleItemEditContext context;
    private final EditingSession session;

    public EnchantmentGui(SimpleItemEditContext context, EditingSession session) {
        this.context = context;
        this.session = session;
    }

    @Override
    public void open(Player player) {
        EnchantmentGuiHolder holder = new EnchantmentGuiHolder(session, context.editors());
        Inventory inventory = Bukkit.createInventory(holder, 54, Component.text("Edit Enchantment"));
        holder.setInventory(inventory);
        setup(inventory);
        player.openInventory(inventory);
    }

    private void setup(Inventory inventory) {
        inventory.setItem(13, session.editingItem());

        inventory.setItem(31, button(Material.ANVIL, "Apply Enchantment"));
        inventory.setItem(49, button(Material.ANVIL, "Combine Books"));
        inventory.setItem(48, button(Material.RED_DYE, "Reset Enchantments"));
        inventory.setItem(53, button(Material.ARROW, "Back"));
    }

    private ItemStack button(Material material, String name) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(Component.text(name));
        item.setItemMeta(meta);

        return item;
    }
}