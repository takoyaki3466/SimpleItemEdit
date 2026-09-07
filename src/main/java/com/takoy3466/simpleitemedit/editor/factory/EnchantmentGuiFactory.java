package com.takoy3466.simpleitemedit.editor.factory;

import com.takoy3466.simpleitemedit.context.SimpleItemEditContext;
import com.takoy3466.simpleitemedit.editor.EditorRegistry;
import com.takoy3466.simpleitemedit.gui.EnchantmentGui;
import com.takoy3466.simpleitemedit.gui.IGui;
import com.takoy3466.simpleitemedit.session.EditingSession;
import org.bukkit.entity.Player;

public class EnchantmentGuiFactory implements IEditorGuiFactory {
    private final SimpleItemEditContext context;

    public EnchantmentGuiFactory(SimpleItemEditContext context) {
        this.context = context;
    }

    @Override
    public String editorId() {
        return "enchantment";
    }

    @Override
    public IGui create(Player player, EditingSession session, EditorRegistry editors) {
        return new EnchantmentGui(context, session);
    }
}