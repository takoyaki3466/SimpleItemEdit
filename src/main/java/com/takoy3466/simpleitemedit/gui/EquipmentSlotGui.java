package com.takoy3466.simpleitemedit.gui;

import com.takoy3466.simpleitemedit.context.GuiEditContext;
import com.takoy3466.simpleitemedit.context.IEditContext;
import com.takoy3466.simpleitemedit.context.SimpleItemEditContext;
import com.takoy3466.simpleitemedit.editor.EquipmentSlotEditor;
import com.takoy3466.simpleitemedit.holder.EquipmentSlotGuiHolder;
import com.takoy3466.simpleitemedit.session.EditingSession;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EquipmentSlotGui implements IGui {
    private final SimpleItemEditContext context;
    private final EditingSession session;

    public EquipmentSlotGui(SimpleItemEditContext context, EditingSession session) {
        this.context = context;
        this.session = session;
    }

    @Override
    public void open(Player player) {
        EquipmentSlotGuiHolder holder = new EquipmentSlotGuiHolder(session, context.editors());
        Inventory inventory = Bukkit.createInventory(holder, 27, Component.text("Equipment Slot"));
        holder.setInventory(inventory);
        setup(player, inventory);
        player.openInventory(inventory);
    }

    private void setup(Player player, Inventory inventory) {
        inventory.setItem(13, session.editingItem());
        EquipmentSlotEditor editor = getEditor();

        if (editor == null) {
            return;
        }

        IEditContext editorContext = new GuiEditContext(player, session, context.editors(),
                () -> new EquipmentSlotGui(context, session),
                () -> new MainEditGui(session, context.editors()));

        EquipmentSlot currentSlot = editor.getSlot(editorContext);

        inventory.setItem(10, slotButton(Material.LEATHER_HELMET, "Head", EquipmentSlot.HEAD, currentSlot));
        inventory.setItem(11, slotButton(Material.LEATHER_CHESTPLATE, "Chest", EquipmentSlot.CHEST, currentSlot));
        inventory.setItem(15, slotButton(Material.LEATHER_LEGGINGS, "Legs", EquipmentSlot.LEGS, currentSlot));
        inventory.setItem(16, slotButton(Material.LEATHER_BOOTS, "Feet", EquipmentSlot.FEET, currentSlot));
        inventory.setItem(20, slotButton(Material.SADDLE, "Body", EquipmentSlot.BODY, currentSlot));
        inventory.setItem(21, slotButton(Material.DIAMOND_SWORD, "Main Hand", EquipmentSlot.HAND, currentSlot));
        inventory.setItem(23, slotButton(Material.SHIELD, "Off Hand", EquipmentSlot.OFF_HAND, currentSlot));

        inventory.setItem(22, button(Material.BARRIER, "Reset"));
        inventory.setItem(26, button(Material.ARROW, "Back"));
    }

    private EquipmentSlotEditor getEditor() {
        if (!(context.editors().get("equipment_slot") instanceof EquipmentSlotEditor editor)) {
            return null;
        }

        return editor;
    }

    private ItemStack slotButton(Material material, String name, EquipmentSlot slot, EquipmentSlot currentSlot) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        String text = name;

        if (slot == currentSlot) {
            text += " [Selected]";
        }

        meta.displayName(Component.text(text));
        item.setItemMeta(meta);

        return item;
    }

    private ItemStack button(Material material, String name) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(Component.text(name));
        item.setItemMeta(meta);

        return item;
    }
}