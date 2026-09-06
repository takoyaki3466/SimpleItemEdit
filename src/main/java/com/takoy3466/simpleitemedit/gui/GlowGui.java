package com.takoy3466.simpleitemedit.gui;

import com.takoy3466.simpleitemedit.context.SimpleItemEditContext;
import com.takoy3466.simpleitemedit.holder.GlowGuiHolder;
import com.takoy3466.simpleitemedit.session.EditingSession;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GlowGui implements IGui {
    private final SimpleItemEditContext context;
    private final EditingSession session;

    public GlowGui(SimpleItemEditContext context, EditingSession session) {
        this.context = context;
        this.session = session;
    }

    @Override
    public void open(Player player) {
        GlowGuiHolder holder = new GlowGuiHolder(session, context.editors());
        Inventory inventory = Bukkit.createInventory(holder, 27, Component.text("Edit Glow"));
        holder.setInventory(inventory);
        setup(inventory);
        player.openInventory(inventory);
    }

    private void setup(Inventory inventory) {
        inventory.setItem(13, session.editingItem());
        inventory.setItem(11, createButton(Material.ENCHANTED_BOOK, "Enable Glow"));
        inventory.setItem(15, createButton(Material.BOOK, "Disable Glow"));
        inventory.setItem(22, createButton(Material.ARROW, "Back"));
    }

    private ItemStack createButton(Material material, String name) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(Component.text(name));
        item.setItemMeta(meta);

        return item;
    }
}