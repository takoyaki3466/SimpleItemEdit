package com.takoy3466.simpleitemedit.gui;

import com.takoy3466.simpleitemedit.editor.EditorRegistry;
import com.takoy3466.simpleitemedit.editor.IItemEditor;
import com.takoy3466.simpleitemedit.holder.MainEditHolder;
import com.takoy3466.simpleitemedit.session.EditingSession;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MainEditGui implements IGui {

    private final EditingSession session;
    private final EditorRegistry editors;

    public MainEditGui(EditingSession session, EditorRegistry editors) {
        this.session = session;
        this.editors = editors;
    }

    @Override
    public void open(Player player) {
        MainEditHolder holder = new MainEditHolder(session, editors);
        Inventory inventory = Bukkit.createInventory(holder, 54, Component.text("SimpleItemEdit"));
        holder.setInventory(inventory);
        setup(holder, inventory);
        player.openInventory(inventory);
    }

    private void setup(MainEditHolder holder, Inventory inventory) {
        inventory.setItem(13, session.editingItem());

        setEditor(holder, inventory, 27, "name");
        setEditor(holder, inventory, 28, "color");
        setEditor(holder, inventory, 29, "style");
        setEditor(holder, inventory, 30, "rarity");
        setEditor(holder, inventory, 31, "glow");
        setEditor(holder, inventory, 32, "equipment_slot");
        setEditor(holder, inventory, 33, "item_model");
        setEditor(holder, inventory, 34, "enchantment");

        inventory.setItem(45, createButton(Material.LIME_DYE, "Apply"));
        inventory.setItem(49, createButton(Material.RED_DYE, "Reset"));
        inventory.setItem(53, createButton(Material.BARRIER, "Close"));
    }

    private void setEditor(MainEditHolder holder, Inventory inventory, int slot, String editorId) {
        IItemEditor editor = editors.get(editorId);

        if (editor == null) {
            return;
        }

        inventory.setItem(slot, editor.createIcon());
        holder.registerEditor(slot, editor);
    }

    private ItemStack createButton(Material material, String name) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(Component.text(name));
        item.setItemMeta(meta);

        return item;
    }
}