package com.takoy3466.simpleitemedit.editor.factory;

import com.takoy3466.simpleitemedit.context.SimpleItemEditContext;
import com.takoy3466.simpleitemedit.editor.EditorRegistry;
import com.takoy3466.simpleitemedit.gui.EnchantmentSplitGui;
import com.takoy3466.simpleitemedit.gui.IGui;
import com.takoy3466.simpleitemedit.session.EditingSession;
import org.bukkit.entity.Player;

public class EnchantmentSplitGuiFactory extends AbstractGuiFactory {
    public EnchantmentSplitGuiFactory(SimpleItemEditContext context) {
        super(context, "enchantment_split");
    }

    @Override
    public IGui create(Player player, EditingSession session, EditorRegistry editors) {
        return new EnchantmentSplitGui(context, session);
    }
}