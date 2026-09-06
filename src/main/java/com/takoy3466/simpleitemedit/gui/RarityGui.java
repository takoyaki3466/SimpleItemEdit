package com.takoy3466.simpleitemedit.gui;

import com.takoy3466.simpleitemedit.context.SimpleItemEditContext;
import com.takoy3466.simpleitemedit.holder.RarityGuiHolder;
import com.takoy3466.simpleitemedit.session.EditingSession;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class RarityGui implements IGui {
    private final SimpleItemEditContext context;
    private final EditingSession session;

    public RarityGui(SimpleItemEditContext context, EditingSession session) {
        this.context = context;
        this.session = session;
    }

    @Override
    public void open(Player player) {
        RarityGuiHolder holder = new RarityGuiHolder(session, context.editors());
        Inventory inventory = Bukkit.createInventory(holder, 27, Component.text("Edit Rarity"));
        holder.setInventory(inventory);
        setup(inventory);
        player.openInventory(inventory);
    }

    private void setup(Inventory inventory) {
        inventory.setItem(13, session.editingItem());
        inventory.setItem(10, button(Material.WHITE_WOOL, "Common"));
        inventory.setItem(11, button(Material.IRON_INGOT, "Uncommon"));
        inventory.setItem(15, button(Material.DIAMOND, "Rare"));
        inventory.setItem(16, button(Material.NETHER_STAR, "Epic"));
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