package com.takoy3466.simpleitemedit.gui;

import com.takoy3466.simpleitemedit.context.GuiEditContext;
import com.takoy3466.simpleitemedit.context.IEditContext;
import com.takoy3466.simpleitemedit.context.SimpleItemEditContext;
import com.takoy3466.simpleitemedit.editor.EquipmentSlotEditor;
import com.takoy3466.simpleitemedit.holder.EquipmentSlotGuiHolder;
import com.takoy3466.simpleitemedit.session.EditingSession;
import com.takoy3466.simpleitemedit.util.GuiUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EquipmentSlotGui extends AbstractGui {
    public EquipmentSlotGui(SimpleItemEditContext context, EditingSession session) {
        super(context, session);
    }

    @Override
    public void open(Player player) {
        Inventory inv = GuiUtil.holderSet(new EquipmentSlotGuiHolder(session, context.editors()), 27, "Equipment Slot");
        setup(player, inv);
        player.openInventory(inv);
    }

    @Override
    protected void setup(Player player, Inventory inv) {
        inv.setItem(13, session.editingItem());
        EquipmentSlotEditor editor = getEditor();

        if (editor == null) {
            return;
        }

        IEditContext editorContext = new GuiEditContext(player, session, context.editors(),
                () -> new EquipmentSlotGui(context, session),
                () -> new MainEditGui(session, context.editors()));

        EquipmentSlot currentSlot = editor.getSlot(editorContext);

        inv.setItem(10, slotButton(Material.LEATHER_HELMET, "Head", EquipmentSlot.HEAD, currentSlot));
        inv.setItem(11, slotButton(Material.LEATHER_CHESTPLATE, "Chest", EquipmentSlot.CHEST, currentSlot));
        inv.setItem(15, slotButton(Material.LEATHER_LEGGINGS, "Legs", EquipmentSlot.LEGS, currentSlot));
        inv.setItem(16, slotButton(Material.LEATHER_BOOTS, "Feet", EquipmentSlot.FEET, currentSlot));
        inv.setItem(20, slotButton(Material.SADDLE, "Body", EquipmentSlot.BODY, currentSlot));
        inv.setItem(21, slotButton(Material.DIAMOND_SWORD, "Main Hand", EquipmentSlot.HAND, currentSlot));
        inv.setItem(23, slotButton(Material.SHIELD, "Off Hand", EquipmentSlot.OFF_HAND, currentSlot));

        inv.setItem(22, GuiUtil.button(Material.BARRIER, "Reset"));
        inv.setItem(26, GuiUtil.button(Material.ARROW, "Back"));
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
}