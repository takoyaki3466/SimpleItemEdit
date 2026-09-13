package com.takoy3466.simpleitemedit.holder;

import com.takoy3466.simpleitemedit.editor.EditorRegistry;
import com.takoy3466.simpleitemedit.editor.IItemEditor;
import com.takoy3466.simpleitemedit.session.EditingSession;

import java.util.HashMap;
import java.util.Map;

public class MainEditHolder extends AbstractGuiHolder {
    private final Map<Integer, IItemEditor> slotEditors = new HashMap<>();

    public MainEditHolder(EditingSession session, EditorRegistry editors) {
        super(session, editors);
    }

    public void registerEditor(int slot, IItemEditor editor) {
        slotEditors.put(slot, editor);
    }

    public IItemEditor getEditor(int slot) {
        return slotEditors.get(slot);
    }
}