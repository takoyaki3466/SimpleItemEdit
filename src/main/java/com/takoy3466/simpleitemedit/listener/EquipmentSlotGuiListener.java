package com.takoy3466.simpleitemedit.listener;

import com.takoy3466.simpleitemedit.context.IEditContext;
import com.takoy3466.simpleitemedit.context.SimpleItemEditContext;
import com.takoy3466.simpleitemedit.editor.EquipmentSlotEditor;
import com.takoy3466.simpleitemedit.gui.MainEditGui;
import com.takoy3466.simpleitemedit.holder.EquipmentSlotGuiHolder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.jspecify.annotations.NonNull;

public class EquipmentSlotGuiListener extends AbstractListener<EquipmentSlotGuiHolder, EquipmentSlotEditor> {
    public EquipmentSlotGuiListener(SimpleItemEditContext context) {
        super(context, "equipment_slot");
    }

    @EventHandler
    @Override
    protected void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        if (!(event.getInventory().getHolder() instanceof EquipmentSlotGuiHolder holder)) {
            return;
        }

        event.setCancelled(true);
        int slot = event.getRawSlot();

        if (slot < 0 || slot >= event.getView().getTopInventory().getSize()) {
            return;
        }

        if (!(getEditor() instanceof EquipmentSlotEditor slotEditor)) {
            return;
        }

        EquipmentSlot equipmentSlot = switch (slot) {
            case 10 -> EquipmentSlot.HEAD;
            case 11 -> EquipmentSlot.CHEST;
            case 15 -> EquipmentSlot.LEGS;
            case 16 -> EquipmentSlot.FEET;
            case 20 -> EquipmentSlot.BODY;
            case 21 -> EquipmentSlot.HAND;
            case 23 -> EquipmentSlot.OFF_HAND;
            default -> null;
        };

        if (equipmentSlot != null) {
            setSlot(player, holder, slotEditor, equipmentSlot);
        }

        switch (slot) {
            case 22 -> reset(player, holder, slotEditor);
            case 26 -> back(player, holder, slotEditor);
            default -> {}
        }
    }

    private void setSlot(Player player, EquipmentSlotGuiHolder holder, EquipmentSlotEditor editor, EquipmentSlot slot) {
        IEditContext editorContext = createContext(player, holder);
        editor.setSlot(editorContext, slot);
        editorContext.refresh();
    }

    @Override
    protected void reset(@NonNull Player player, @NonNull EquipmentSlotGuiHolder holder, @NonNull EquipmentSlotEditor editor) {
        IEditContext editorContext = createContext(player, holder);
        editor.reset(editorContext);
    }

    @Override
    protected void back(@NonNull Player player, @NonNull EquipmentSlotGuiHolder holder, @NonNull EquipmentSlotEditor editor) {
        new MainEditGui(holder.session(), context.editors()).open(player);
    }
}