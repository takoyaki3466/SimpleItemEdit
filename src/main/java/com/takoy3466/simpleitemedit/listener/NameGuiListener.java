package com.takoy3466.simpleitemedit.listener;

import com.takoy3466.simpleitemedit.context.IEditContext;
import com.takoy3466.simpleitemedit.context.SimpleItemEditContext;
import com.takoy3466.simpleitemedit.editor.NameEditor;
import com.takoy3466.simpleitemedit.gui.MainEditGui;
import com.takoy3466.simpleitemedit.holder.NameGuiHolder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jspecify.annotations.NonNull;

public class NameGuiListener extends AbstractListener<NameGuiHolder, NameEditor> {
    public NameGuiListener(SimpleItemEditContext context) {
        super(context, "name");
    }

    @EventHandler
    @Override
    public void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        if (!(event.getInventory().getHolder() instanceof NameGuiHolder holder)) {
            return;
        }

        event.setCancelled(true);
        int slot = event.getRawSlot();

        if (slot < 0 || slot >= event.getView().getTopInventory().getSize()) {
            return;
        }

        if (!(getEditor() instanceof NameEditor nameEditor)) {
            return;
        }

        switch (slot) {
            case 11 -> startNameInput(player, holder, nameEditor);
            case 15 -> reset(player, holder, nameEditor);
            case 22 -> back(player, holder, nameEditor);
            default -> {}
        }
    }

    private void startNameInput(Player player, NameGuiHolder holder, NameEditor nameEditor) {
        player.closeInventory();

        context.inputHandler().start(player, name -> Bukkit.getScheduler().runTask(context.plugin(), () -> {
            IEditContext editContext = createContext(player, holder);
            nameEditor.setName(editContext, name);
            editContext.refresh();
        }));
    }

    @Override
    public void reset(@NonNull Player player, @NonNull NameGuiHolder holder, @NonNull NameEditor editor) {
        IEditContext editContext = createContext(player, holder);
        editor.reset(editContext);
    }

    @Override
    protected void back(@NonNull Player player, @NonNull NameGuiHolder holder, @NonNull NameEditor editor) {
        new MainEditGui(holder.session(), context.editors()).open(player);
    }

}