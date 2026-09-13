package com.takoy3466.simpleitemedit.editor.factory;

import com.takoy3466.simpleitemedit.context.SimpleItemEditContext;
import com.takoy3466.simpleitemedit.editor.EditorRegistry;
import com.takoy3466.simpleitemedit.gui.IGui;
import com.takoy3466.simpleitemedit.gui.StyleGui;
import com.takoy3466.simpleitemedit.session.EditingSession;
import org.bukkit.entity.Player;

public class StyleGuiFactory extends AbstractGuiFactory {
    public StyleGuiFactory(SimpleItemEditContext context) {
        super(context, "style");
    }

    @Override
    public IGui create(Player player, EditingSession session, EditorRegistry editors) {
        return new StyleGui(context, session);
    }
}