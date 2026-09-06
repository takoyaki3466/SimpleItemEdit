package com.takoy3466.simpleitemedit.context;

import com.takoy3466.simpleitemedit.editor.EditorGuiRegistry;
import com.takoy3466.simpleitemedit.editor.EditorRegistry;
import com.takoy3466.simpleitemedit.input.IInputHandler;
import com.takoy3466.simpleitemedit.itemmodel.ItemModelRegistry;
import com.takoy3466.simpleitemedit.session.EditingSessionManager;
import org.bukkit.plugin.Plugin;

public interface SimpleItemEditContext {

    Plugin plugin();

    EditorRegistry editors();

    EditorGuiRegistry guiRegistry();

    EditingSessionManager sessions();

    IInputHandler inputHandler();

    ItemModelRegistry itemModelRegistry();
}
