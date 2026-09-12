package com.takoy3466.simpleitemedit.editor.factory;

import com.takoy3466.simpleitemedit.context.SimpleItemEditContext;
import com.takoy3466.simpleitemedit.editor.EditorRegistry;
import com.takoy3466.simpleitemedit.gui.EnchantmentApplyGui;
import com.takoy3466.simpleitemedit.gui.IGui;
import com.takoy3466.simpleitemedit.session.EditingSession;
import org.bukkit.entity.Player;

public class EnchantmentApplyGuiFactory implements IEditorGuiFactory {
    private final SimpleItemEditContext context;

    public EnchantmentApplyGuiFactory(SimpleItemEditContext context) {
        this.context = context;
    }

    @Override
    public String editorId() {
        return "enchantment_apply";
    }

    @Override
    public IGui create(Player player, EditingSession session, EditorRegistry editors) {
        return new EnchantmentApplyGui(context, session);
    }
}