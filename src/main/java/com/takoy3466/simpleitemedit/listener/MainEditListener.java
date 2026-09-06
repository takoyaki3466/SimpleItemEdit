package com.takoy3466.simpleitemedit.listener;

import com.takoy3466.simpleitemedit.editor.factory.IEditorGuiFactory;
import com.takoy3466.simpleitemedit.editor.EditorGuiRegistry;
import com.takoy3466.simpleitemedit.editor.IItemEditor;
import com.takoy3466.simpleitemedit.gui.IGui;
import com.takoy3466.simpleitemedit.gui.MainEditGui;
import com.takoy3466.simpleitemedit.holder.MainEditHolder;
import com.takoy3466.simpleitemedit.session.EditingSessionManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MainEditListener implements Listener {

    private final EditorGuiRegistry guiRegistry;
    private final EditingSessionManager sessionManager;

    public MainEditListener(EditorGuiRegistry guiRegistry, EditingSessionManager sessionManager) {
        this.guiRegistry = guiRegistry;
        this.sessionManager = sessionManager;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        if (!(event.getInventory().getHolder() instanceof MainEditHolder holder)) {
            return;
        }

        event.setCancelled(true);
        int slot = event.getRawSlot();

        if (slot < 0 || slot >= event.getView().getTopInventory().getSize()) {
            return;
        }

        if (slot == 45) {
            apply(player, holder);
            return;
        }

        if (slot == 49) {
            reset(player, holder);
            return;
        }

        if (slot == 53) {
            close(player);
            return;
        }

        IItemEditor editor = holder.getEditor(slot);

        if (editor == null) {
            return;
        }

        IEditorGuiFactory factory = guiRegistry.get(editor.id());

        if (factory == null) {
            return;
        }

        IGui gui = factory.create(player, holder.session(), holder.editors());

        gui.open(player);
    }

    private void apply(Player player, MainEditHolder holder) {
        holder.session().apply();
        sessionManager.remove(player);
        player.closeInventory();
    }

    private void reset(Player player, MainEditHolder holder) {
        holder.session().reset();
        new MainEditGui(holder.session(), holder.editors()).open(player);
    }

    private void close(Player player) {
        player.closeInventory();
    }
}