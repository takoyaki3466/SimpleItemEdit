package com.takoy3466.simpleitemedit.editor.factory;

import com.takoy3466.simpleitemedit.context.SimpleItemEditContext;
import com.takoy3466.simpleitemedit.editor.EditorRegistry;
import com.takoy3466.simpleitemedit.gui.IGui;
import com.takoy3466.simpleitemedit.gui.ItemModelGui;
import com.takoy3466.simpleitemedit.session.EditingSession;
import org.bukkit.entity.Player;

public class ItemModelGuiFactory implements IEditorGuiFactory {
    private final SimpleItemEditContext context;

    public ItemModelGuiFactory(SimpleItemEditContext context) {
        this.context = context;
    }

    @Override
    public String editorId() {
        return "item_model";
    }

    @Override
    public IGui create(Player player, EditingSession session, EditorRegistry editors) {
        return new ItemModelGui(context, session);
    }
}