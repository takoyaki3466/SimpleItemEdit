package com.takoy3466.simpleitemedit.listener;

import com.takoy3466.simpleitemedit.context.GuiEditContext;
import com.takoy3466.simpleitemedit.context.IEditContext;
import com.takoy3466.simpleitemedit.context.SimpleItemEditContext;
import com.takoy3466.simpleitemedit.editor.EquipmentSlotEditor;
import com.takoy3466.simpleitemedit.editor.IItemEditor;
import com.takoy3466.simpleitemedit.gui.EquipmentSlotGui;
import com.takoy3466.simpleitemedit.gui.MainEditGui;
import com.takoy3466.simpleitemedit.holder.EquipmentSlotGuiHolder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.EquipmentSlot;

public class EquipmentSlotGuiListener implements Listener {
    private final SimpleItemEditContext context;

    public EquipmentSlotGuiListener(SimpleItemEditContext context) {
        this.context = context;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
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

        IItemEditor editor = context.editors().get("equipment_slot");

        if (!(editor instanceof EquipmentSlotEditor slotEditor)) {
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
            IEditContext editorContext = createContext(player, holder);
            slotEditor.setSlot(editorContext, equipmentSlot);
            editorContext.refresh();

            return;
        }

        if (slot == 22) {
            IEditContext editorContext = createContext(player, holder);
            slotEditor.reset(editorContext);

            return;
        }

        if (slot == 26) {
            new MainEditGui(holder.session(), context.editors()).open(player);
        }
    }

    private IEditContext createContext(Player player, EquipmentSlotGuiHolder holder) {
        return new GuiEditContext(player, holder.session(), context.editors(),
                () -> new EquipmentSlotGui(context, holder.session()),
                () -> new MainEditGui(holder.session(), context.editors()));
    }
}