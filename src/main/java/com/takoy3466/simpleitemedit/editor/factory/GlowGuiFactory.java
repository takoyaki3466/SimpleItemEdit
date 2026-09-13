package com.takoy3466.simpleitemedit.editor.factory;

import com.takoy3466.simpleitemedit.context.SimpleItemEditContext;
import com.takoy3466.simpleitemedit.editor.EditorRegistry;
import com.takoy3466.simpleitemedit.gui.GlowGui;
import com.takoy3466.simpleitemedit.gui.IGui;
import com.takoy3466.simpleitemedit.session.EditingSession;
import org.bukkit.entity.Player;

public class GlowGuiFactory extends AbstractGuiFactory {

    public GlowGuiFactory(SimpleItemEditContext context) {
        super(context, "glow");
    }

    @Override
    public IGui create(Player player, EditingSession session, EditorRegistry editors) {
        return new GlowGui(context, session);
    }
}