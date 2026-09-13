package com.takoy3466.simpleitemedit.editor.factory;

import com.takoy3466.simpleitemedit.context.SimpleItemEditContext;

public abstract class AbstractGuiFactory implements IEditorGuiFactory {
    protected final SimpleItemEditContext context;
    private final String id;

    protected AbstractGuiFactory(SimpleItemEditContext context, String id) {
        this.context = context;
        this.id = id;
    }

    @Override
    public String editorId() {
        return id;
    }
}
