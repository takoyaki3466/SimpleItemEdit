package com.takoy3466.simpleitemedit.context;

import com.takoy3466.simpleitemedit.editor.EditorGuiRegistry;
import com.takoy3466.simpleitemedit.editor.EditorRegistry;
import com.takoy3466.simpleitemedit.input.IInputHandler;
import com.takoy3466.simpleitemedit.itemmodel.ItemModelRegistry;
import com.takoy3466.simpleitemedit.session.EditingSessionManager;
import org.bukkit.plugin.Plugin;

public class SimpleItemEditContextImpl implements SimpleItemEditContext {

    private final Plugin plugin;
    private final EditorRegistry editors;
    private final EditorGuiRegistry guiRegistry;
    private final EditingSessionManager sessions;
    private final IInputHandler inputHandler;
    private final ItemModelRegistry itemModelRegistry;

    public SimpleItemEditContextImpl(Plugin plugin, EditorRegistry editors, EditorGuiRegistry guiRegistry, EditingSessionManager sessions, IInputHandler inputHandler, ItemModelRegistry itemModelRegistry) {
        this.plugin = plugin;
        this.editors = editors;
        this.guiRegistry = guiRegistry;
        this.sessions = sessions;
        this.inputHandler = inputHandler;
        this.itemModelRegistry = itemModelRegistry;
    }

    @Override
    public Plugin plugin() {
        return plugin;
    }

    @Override
    public EditorRegistry editors() {
        return editors;
    }

    @Override
    public EditorGuiRegistry guiRegistry() {
        return guiRegistry;
    }

    @Override
    public EditingSessionManager sessions() {
        return sessions;
    }

    @Override
    public IInputHandler inputHandler() {
        return inputHandler;
    }

    @Override
    public ItemModelRegistry itemModelRegistry() {
        return itemModelRegistry;
    }
}
