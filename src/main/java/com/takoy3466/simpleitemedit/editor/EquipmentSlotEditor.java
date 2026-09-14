package com.takoy3466.simpleitemedit.editor;

import com.takoy3466.simpleitemedit.context.IEditContext;
import org.bukkit.Material;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.EquippableComponent;

public class EquipmentSlotEditor extends AbstractItemEditor {
    public EquipmentSlotEditor() {
        super("equipment_slot", Material.ARMOR_STAND, "Equipment Slot");
    }

    public void setSlot(IEditContext context, EquipmentSlot slot) {
        ItemStack item = context.session().editingItem();
        ItemMeta meta = item.getItemMeta();

        if (meta == null) {
            return;
        }

        EquippableComponent equippable = meta.getEquippable();
        equippable.setSlot(slot);
        meta.setEquippable(equippable);
        item.setItemMeta(meta);
        context.session().setEditingItem(item);
    }

    public EquipmentSlot getSlot(IEditContext context) {
        ItemStack item = context.session().editingItem();
        ItemMeta meta = item.getItemMeta();

        if (meta == null) {
            return null;
        }

        if (!meta.hasEquippable()) {
            return null;
        }

        return meta.getEquippable().getSlot();
    }

    @Override
    public void reset(IEditContext context) {
        ItemStack original = context.session().originalItem();
        ItemStack item = context.session().editingItem();

        ItemMeta originalMeta = original.getItemMeta();
        ItemMeta meta = item.getItemMeta();

        if (originalMeta == null || meta == null) {
            return;
        }

        if (!originalMeta.hasEquippable()) {
            meta.setEquippable(null);

        } else {
            EquippableComponent originalEquippable = originalMeta.getEquippable();
            EquippableComponent equippable = meta.getEquippable();
            equippable.setSlot(originalEquippable.getSlot());
            meta.setEquippable(equippable);
        }

        item.setItemMeta(meta);
        context.session().setEditingItem(item);

        context.refresh();
    }
}