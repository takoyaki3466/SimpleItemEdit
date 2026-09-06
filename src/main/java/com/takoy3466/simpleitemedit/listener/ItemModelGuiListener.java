package com.takoy3466.simpleitemedit.listener;

import com.takoy3466.simpleitemedit.context.GuiEditContext;
import com.takoy3466.simpleitemedit.context.IEditContext;
import com.takoy3466.simpleitemedit.context.SimpleItemEditContext;
import com.takoy3466.simpleitemedit.editor.IItemEditor;
import com.takoy3466.simpleitemedit.editor.ItemModelEditor;
import com.takoy3466.simpleitemedit.gui.ItemModelGui;
import com.takoy3466.simpleitemedit.gui.MainEditGui;
import com.takoy3466.simpleitemedit.holder.ItemModelGuiHolder;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ItemModelGuiListener implements Listener {
    private final SimpleItemEditContext context;

    public ItemModelGuiListener(SimpleItemEditContext context) {
        this.context = context;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
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

        IItemEditor editor = context.editors().get("item_model");

        if (!(editor instanceof ItemModelEditor modelEditor)) {
            return;
        }

        if (slot == 11) {
            startInput(player, holder, modelEditor);
            return;
        }

        if (slot == 15) {
            IEditContext editorContext = createContext(player, holder);
            modelEditor.reset(editorContext);
            return;
        }

        if (slot == 22) {
            context.inputHandler().cancel(player);
            new MainEditGui(holder.session(), context.editors()).open(player);
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

    private IEditContext createContext(Player player, ItemModelGuiHolder holder) {
        return new GuiEditContext(player, holder.session(), context.editors(),
                () -> new ItemModelGui(context, holder.session()),
                () -> new MainEditGui(holder.session(), context.editors()));
    }
}