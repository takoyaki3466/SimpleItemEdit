package com.takoy3466.simpleitemedit.holder;

import com.takoy3466.simpleitemedit.editor.EditorRegistry;
import com.takoy3466.simpleitemedit.editor.IItemEditor;
import com.takoy3466.simpleitemedit.session.EditingSession;

import java.util.HashMap;
import java.util.Map;

public class MainEditHolder extends AbstractGuiHolder {
    private final EditingSession session;
    private final EditorRegistry editors;

    private final Map<Integer, IItemEditor> slotEditors = new HashMap<>();

    public MainEditHolder(EditingSession session, EditorRegistry editors) {
        this.session = session;
        this.editors = editors;
    }

    public EditingSession session() {
        return session;
    }

    public EditorRegistry editors() {
        return editors;
    }

    public void registerEditor(int slot, IItemEditor editor) {
        slotEditors.put(slot, editor);
    }

    public IItemEditor getEditor(int slot) {
        return slotEditors.get(slot);
    }
}