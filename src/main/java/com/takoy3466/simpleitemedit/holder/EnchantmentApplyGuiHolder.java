package com.takoy3466.simpleitemedit.holder;

import com.takoy3466.simpleitemedit.editor.EditorRegistry;
import com.takoy3466.simpleitemedit.session.EditingSession;

public class EnchantmentApplyGuiHolder extends AbstractGuiHolder {
    public static final int TARGET_SLOT = 13;

    public static final int BOOK_SLOT = 29;
    public static final int APPLY_BUTTON_SLOT = 31;

    public static final int RESET_SLOT = 48;
    public static final int BACK_SLOT = 53;

    private final EditingSession session;
    private final EditorRegistry editors;

    public EnchantmentApplyGuiHolder(EditingSession session, EditorRegistry editors) {
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