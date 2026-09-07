package com.takoy3466.simpleitemedit.holder;

import com.takoy3466.simpleitemedit.editor.EditorRegistry;
import com.takoy3466.simpleitemedit.session.EditingSession;

public class EnchantmentGuiHolder extends AbstractGuiHolder {

    public static final int TARGET_SLOT = 13;

    public static final int APPLY_BOOK_SLOT = 29;
    public static final int APPLY_BUTTON_SLOT = 31;

    public static final int MERGE_FIRST_SLOT = 38;
    public static final int MERGE_RESULT_SLOT = 40;
    public static final int MERGE_SECOND_SLOT = 42;

    public static final int RESET_SLOT = 48;
    public static final int MERGE_BUTTON_SLOT = 49;
    public static final int BACK_SLOT = 53;

    private final EditingSession session;
    private final EditorRegistry editors;

    public EnchantmentGuiHolder(EditingSession session, EditorRegistry editors) {
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