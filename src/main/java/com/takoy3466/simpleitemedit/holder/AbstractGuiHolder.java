package com.takoy3466.simpleitemedit.holder;

import com.takoy3466.simpleitemedit.editor.EditorRegistry;
import com.takoy3466.simpleitemedit.session.EditingSession;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jspecify.annotations.NonNull;

public abstract class AbstractGuiHolder implements InventoryHolder {
    private final EditingSession session;
    private final EditorRegistry editors;
    private Inventory inventory;

    public AbstractGuiHolder(EditingSession session, EditorRegistry editors) {
        this.session = session;
        this.editors = editors;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public EditingSession session() {
        return session;
    }

    public EditorRegistry editors() {
        return editors;
    }

    @Override
    public @NonNull Inventory getInventory() {
        return inventory;
    }
}
