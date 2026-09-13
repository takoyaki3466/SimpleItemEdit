package com.takoy3466.simpleitemedit.gui;

import com.takoy3466.simpleitemedit.context.SimpleItemEditContext;
import com.takoy3466.simpleitemedit.session.EditingSession;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public abstract class AbstractGui implements IGui {
    protected final SimpleItemEditContext context;
    protected final EditingSession session;

    public AbstractGui(SimpleItemEditContext context, EditingSession session) {
        this.context = context;
        this.session = session;
    }

    protected void setup(Inventory inv) {
    }

    protected void setup(Player player, Inventory inv) {
    }
}
