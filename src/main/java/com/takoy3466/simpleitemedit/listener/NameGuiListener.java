package com.takoy3466.simpleitemedit.listener;

import com.takoy3466.simpleitemedit.context.SimpleItemEditContext;
import com.takoy3466.simpleitemedit.context.GuiEditContext;
import com.takoy3466.simpleitemedit.editor.IItemEditor;
import com.takoy3466.simpleitemedit.editor.NameEditor;
import com.takoy3466.simpleitemedit.gui.MainEditGui;
import com.takoy3466.simpleitemedit.gui.NameGui;
import com.takoy3466.simpleitemedit.holder.NameGuiHolder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class NameGuiListener implements Listener {
    private final SimpleItemEditContext context;

    public NameGuiListener(SimpleItemEditContext context) {
        this.context = context;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        if (!(event.getInventory().getHolder()
                instanceof NameGuiHolder holder)) {
            return;
        }

        event.setCancelled(true);
        int slot = event.getRawSlot();

        if (slot < 0 || slot >= event.getView().getTopInventory().getSize()) {
            return;
        }

        switch (slot) {
            case 11 -> startNameInput(player, holder);
            case 15 -> reset(player, holder);
            case 22 -> back(player, holder);
            default -> {
            }
        }
    }

    private void startNameInput(Player player, NameGuiHolder holder) {
        IItemEditor editor = context.editors().get("name");

        if (!(editor instanceof NameEditor nameEditor)) {
            return;
        }

        player.closeInventory();

        context.inputHandler().start(player, name -> Bukkit.getScheduler().runTask(context.plugin(), () -> {
            GuiEditContext editorContext = createEditorContext(player, holder);
            nameEditor.setName(editorContext, name);
            editorContext.refresh();
        }));
    }

    private void reset(Player player, NameGuiHolder holder) {
        IItemEditor editor = context.editors().get("name");

        if (editor == null) {
            return;
        }

        GuiEditContext editorContext = createEditorContext(player, holder);
        editor.reset(editorContext);
    }

    private void back(Player player, NameGuiHolder holder) {
        new MainEditGui(holder.session(), context.editors()).open(player);
    }

    private GuiEditContext createEditorContext(Player player, NameGuiHolder holder) {
        return new GuiEditContext(player, holder.session(), context.editors(),
                () -> new NameGui(context, holder.session()),
                () -> new MainEditGui(holder.session(), context.editors())
        );
    }
}