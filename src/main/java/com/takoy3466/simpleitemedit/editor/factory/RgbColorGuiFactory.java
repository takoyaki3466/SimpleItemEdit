package com.takoy3466.simpleitemedit.editor.factory;

import com.takoy3466.simpleitemedit.context.SimpleItemEditContext;
import com.takoy3466.simpleitemedit.editor.EditorRegistry;
import com.takoy3466.simpleitemedit.gui.IGui;
import com.takoy3466.simpleitemedit.gui.RgbColorGui;
import com.takoy3466.simpleitemedit.session.EditingSession;
import org.bukkit.entity.Player;

public class RgbColorGuiFactory implements IEditorGuiFactory {
    private final SimpleItemEditContext context;

    public RgbColorGuiFactory(SimpleItemEditContext context) {
        this.context = context;
    }

    @Override
    public String editorId() {
        return "rgb_color";
    }

    @Override
    public IGui create(Player player, EditingSession session, EditorRegistry editors) {
        return new RgbColorGui(context, session);
    }
}