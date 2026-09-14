package com.takoy3466.simpleitemedit.holder;

import com.takoy3466.simpleitemedit.editor.EditorRegistry;
import com.takoy3466.simpleitemedit.session.EditingSession;

public class EnchantmentSplitGuiHolder extends AbstractGuiHolder {
    public static final int TARGET_BOOK_SLOT = 1;

    public static final int RESULT_START_SLOT = 10;
    public static final int RESULT_END_SLOT = 44;

    public static final int RESET_SLOT = 49;
    public static final int BACK_SLOT = 53;

    public EnchantmentSplitGuiHolder(EditingSession session, EditorRegistry editors) {
        super(session, editors);
    }
}