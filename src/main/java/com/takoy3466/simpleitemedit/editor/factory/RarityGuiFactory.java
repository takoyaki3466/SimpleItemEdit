package com.takoy3466.simpleitemedit.editor.factory;

import com.takoy3466.simpleitemedit.context.SimpleItemEditContext;
import com.takoy3466.simpleitemedit.editor.EditorRegistry;
import com.takoy3466.simpleitemedit.gui.IGui;
import com.takoy3466.simpleitemedit.gui.RarityGui;
import com.takoy3466.simpleitemedit.session.EditingSession;
import org.bukkit.entity.Player;

public class RarityGuiFactory implements IEditorGuiFactory {
    private final SimpleItemEditContext context;

    public RarityGuiFactory(SimpleItemEditContext context) {
        this.context = context;
    }

    @Override
    public String editorId() {
        return "rarity";
    }

    @Override
    public IGui create(Player player, EditingSession session, EditorRegistry editors) {
        return new RarityGui(context, session);
    }
}