package com.takoy3466.simpleitemedit.gui;

import com.takoy3466.simpleitemedit.context.SimpleItemEditContext;
import com.takoy3466.simpleitemedit.holder.ItemModelGuiHolder;
import com.takoy3466.simpleitemedit.session.EditingSession;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemModelGui implements IGui {
    private final SimpleItemEditContext context;
    private final EditingSession session;

    public ItemModelGui(SimpleItemEditContext context, EditingSession session) {
        this.context = context;
        this.session = session;
    }

    @Override
    public void open(Player player) {
        ItemModelGuiHolder holder = new ItemModelGuiHolder(session, context.editors());
        Inventory inventory = Bukkit.createInventory(holder, 27, Component.text("Edit Item Model"));
        holder.setInventory(inventory);
        setup(inventory);
        player.openInventory(inventory);
    }

    private void setup(Inventory inventory) {
        inventory.setItem(13, session.editingItem());

        inventory.setItem(11, button(Material.NAME_TAG, "Change Item Model"));

        inventory.setItem(15, button(Material.BARRIER, "Reset"));
        inventory.setItem(22, button(Material.ARROW, "Back"));
    }

    private ItemStack button(Material material, String name) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(Component.text(name));
        item.setItemMeta(meta);

        return item;
    }
}