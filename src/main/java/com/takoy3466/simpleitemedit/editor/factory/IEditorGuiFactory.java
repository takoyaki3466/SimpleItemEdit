package com.takoy3466.simpleitemedit.editor.factory;

import com.takoy3466.simpleitemedit.editor.EditorRegistry;
import com.takoy3466.simpleitemedit.gui.IGui;
import com.takoy3466.simpleitemedit.session.EditingSession;
import org.bukkit.entity.Player;

public interface IEditorGuiFactory {

    String editorId();

    IGui create(Player player, EditingSession session, EditorRegistry editors);
}
