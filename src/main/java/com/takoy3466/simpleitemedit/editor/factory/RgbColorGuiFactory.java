package com.takoy3466.simpleitemedit.editor.factory;

import com.takoy3466.simpleitemedit.context.SimpleItemEditContext;
import com.takoy3466.simpleitemedit.editor.EditorRegistry;
import com.takoy3466.simpleitemedit.gui.IGui;
import com.takoy3466.simpleitemedit.gui.RgbColorGui;
import com.takoy3466.simpleitemedit.session.EditingSession;
import org.bukkit.entity.Player;

public class RgbColorGuiFactory extends AbstractGuiFactory {
    public RgbColorGuiFactory(SimpleItemEditContext context) {
        super(context, "rgb_color");
    }

    @Override
    public IGui create(Player player, EditingSession session, EditorRegistry editors) {
        return new RgbColorGui(context, session);
    }
}