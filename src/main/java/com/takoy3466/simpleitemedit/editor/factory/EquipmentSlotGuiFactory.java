package com.takoy3466.simpleitemedit.editor.factory;

import com.takoy3466.simpleitemedit.context.SimpleItemEditContext;
import com.takoy3466.simpleitemedit.editor.EditorRegistry;
import com.takoy3466.simpleitemedit.gui.EquipmentSlotGui;
import com.takoy3466.simpleitemedit.gui.IGui;
import com.takoy3466.simpleitemedit.session.EditingSession;
import org.bukkit.entity.Player;

public class EquipmentSlotGuiFactory extends AbstractGuiFactory {
    public EquipmentSlotGuiFactory(SimpleItemEditContext context) {
        super(context, "equipment_slot");
    }

    @Override
    public IGui create(Player player, EditingSession session, EditorRegistry editors) {
        return new EquipmentSlotGui(context, session);
    }
}