package com.takoy3466.simpleitemedit.editor;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class EditorRegistry {
    private final Map<String, IItemEditor> editors = new LinkedHashMap<>();

    public void register(IItemEditor editor) {
        String id = editor.id().toLowerCase();

        if (editors.containsKey(id)) {
            throw new IllegalStateException("Editor already registered: " + id);
        }

        editors.put(id, editor);
    }

    public void unregister(String id) {
        editors.remove(id.toLowerCase());
    }

    public IItemEditor get(String id) {
        if (id == null) {
            return null;
        }

        return editors.get(id.toLowerCase());
    }

    public boolean contains(String id) {
        return get(id) != null;
    }

    public Collection<IItemEditor> getEditors() {
        return editors.values();
    }
}
