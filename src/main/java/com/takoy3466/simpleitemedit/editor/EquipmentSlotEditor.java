package com.takoy3466.simpleitemedit.editor;

import com.takoy3466.simpleitemedit.context.IEditContext;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.EquippableComponent;

public class EquipmentSlotEditor implements IItemEditor {

    @Override
    public String id() {
        return "equipment_slot";
    }

    @Override
    public Component displayName() {
        return Component.text("Equipment Slot");
    }

    @Override
    public ItemStack createIcon() {
        ItemStack item = new ItemStack(Material.ARMOR_STAND);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text("Equipment Slot"));
        item.setItemMeta(meta);

        return item;
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