package com.takoy3466.simpleitemedit.context;

import com.takoy3466.simpleitemedit.editor.EditorRegistry;
import com.takoy3466.simpleitemedit.gui.IGui;
import com.takoy3466.simpleitemedit.session.EditingSession;
import org.bukkit.entity.Player;

import java.util.function.Supplier;

public class GuiEditContext implements IEditContext {

    private final Player player;
    private final EditingSession session;
    private final EditorRegistry editors;

    private final Supplier<IGui> refreshGui;
    private final Supplier<IGui> backGui;

    public GuiEditContext(Player player, EditingSession session, EditorRegistry editors, Supplier<IGui> refreshGui, Supplier<IGui> backGui) {
        this.player = player;
        this.session = session;
        this.editors = editors;
        this.refreshGui = refreshGui;
        this.backGui = backGui;
    }

    @Override
    public Player player() {
        return player;
    }

    @Override
    public EditingSession session() {
        return session;
    }

    @Override
    public EditorRegistry editors() {
        return editors;
    }

    @Override
    public void refresh() {
        refreshGui.get().open(player);
    }

    @Override
    public void back() {
        backGui.get().open(player);
    }

    @Override
    public void close() {
        player.closeInventory();
    }
}