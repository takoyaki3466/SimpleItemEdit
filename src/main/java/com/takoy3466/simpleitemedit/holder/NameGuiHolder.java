package com.takoy3466.simpleitemedit.holder;

import com.takoy3466.simpleitemedit.editor.EditorRegistry;
import com.takoy3466.simpleitemedit.session.EditingSession;

public class NameGuiHolder extends AbstractGuiHolder {

    private final EditingSession session;
    private final EditorRegistry editors;

    public NameGuiHolder(EditingSession session, EditorRegistry editors) {
        this.session = session;
        this.editors = editors;
    }

    public EditingSession session() {
        return session;
    }

    public EditorRegistry editors() {
        return editors;
    }
}