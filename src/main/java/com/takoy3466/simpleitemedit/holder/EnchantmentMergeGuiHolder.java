package com.takoy3466.simpleitemedit.holder;

import com.takoy3466.simpleitemedit.editor.EditorRegistry;
import com.takoy3466.simpleitemedit.session.EditingSession;

public class EnchantmentMergeGuiHolder extends AbstractGuiHolder {
    public static final int FIRST_BOOK_SLOT = 29;
    public static final int RESULT_SLOT = 31;
    public static final int SECOND_BOOK_SLOT = 33;

    public static final int MERGE_BUTTON_SLOT = 40;
    public static final int BACK_SLOT = 49;

    public EnchantmentMergeGuiHolder(EditingSession session, EditorRegistry editors) {
        super(session, editors);
    }
}