package com.takoy3466.simpleitemedit.listener;

import com.takoy3466.simpleitemedit.context.IEditContext;
import com.takoy3466.simpleitemedit.context.SimpleItemEditContext;
import com.takoy3466.simpleitemedit.editor.ItemModelEditor;
import com.takoy3466.simpleitemedit.gui.MainEditGui;
import com.takoy3466.simpleitemedit.holder.ItemModelGuiHolder;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jspecify.annotations.NonNull;

public class ItemModelGuiListener extends AbstractListener<ItemModelGuiHolder, ItemModelEditor> {
    public ItemModelGuiListener(SimpleItemEditContext context) {
        super(context, "item_model");
    }

    @EventHandler
    @Override
    protected void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        if (!(event.getInventory().getHolder() instanceof ItemModelGuiHolder holder)) {
            return;
        }

        event.setCancelled(true);
        int slot = event.getRawSlot();

        if (slot < 0 || slot >= event.getView().getTopInventory().getSize()) {
            return;
        }

        if (!(getEditor() instanceof ItemModelEditor modelEditor)) {
            return;
        }

        switch (slot) {
            case 11 -> startInput(player, holder, modelEditor);
            case 15 -> reset(player, holder, modelEditor);
            case 22 -> back(player, holder, modelEditor);
            default -> {}
        }
    }

    private void startInput(Player player, ItemModelGuiHolder holder, ItemModelEditor modelEditor) {
        player.closeInventory();

        context.inputHandler().start(player, context.itemModelRegistry().getModels(),
                input -> {
                    Bukkit.getScheduler().runTask(context.plugin(), () -> {
                        NamespacedKey key = NamespacedKey.fromString(input);

                        if (key == null) {
                            player.sendMessage("§cInvalid Item Model ID: " + input);
                            return;
                        }

                        IEditContext editorContext = createContext(player, holder);
                        modelEditor.setItemModel(editorContext, key);
                        editorContext.refresh();
                    });
                });
    }

    @Override
    protected void reset(@NonNull Player player, @NonNull ItemModelGuiHolder holder, @NonNull ItemModelEditor editor) {
        IEditContext editorContext = createContext(player, holder);
        editor.reset(editorContext);
    }

    @Override
    protected void back(@NonNull Player player, @NonNull ItemModelGuiHolder holder, @NonNull ItemModelEditor editor) {
        context.inputHandler().cancel(player);
        new MainEditGui(holder.session(), context.editors()).open(player);
    }
}