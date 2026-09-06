package com.takoy3466.simpleitemedit.editor;

import com.takoy3466.simpleitemedit.editor.factory.IEditorGuiFactory;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class EditorGuiRegistry {
    private final Map<String, IEditorGuiFactory> factories = new LinkedHashMap<>();

    public void register(IEditorGuiFactory factory) {
        String id = factory.editorId().toLowerCase();

        if (factories.containsKey(id)) {
            throw new IllegalStateException("Editor GUI already registered: " + id);
        }

        factories.put(id, factory);
    }

    public void unregister(String editorId) {
        factories.remove(editorId.toLowerCase());
    }

    public IEditorGuiFactory get(String editorId) {
        if (editorId == null) {
            return null;
        }

        return factories.get(editorId.toLowerCase());
    }

    public boolean contains(String editorId) {
        return get(editorId) != null;
    }

    public Collection<IEditorGuiFactory> getFactories() {
        return factories.values();
    }
}