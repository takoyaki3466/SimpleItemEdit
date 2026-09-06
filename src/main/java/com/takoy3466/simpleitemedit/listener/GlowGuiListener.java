package com.takoy3466.simpleitemedit.listener;

import com.takoy3466.simpleitemedit.context.SimpleItemEditContext;
import com.takoy3466.simpleitemedit.editor.GlowEditor;
import com.takoy3466.simpleitemedit.context.GuiEditContext;
import com.takoy3466.simpleitemedit.editor.IItemEditor;
import com.takoy3466.simpleitemedit.gui.GlowGui;
import com.takoy3466.simpleitemedit.gui.MainEditGui;
import com.takoy3466.simpleitemedit.holder.GlowGuiHolder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GlowGuiListener implements Listener {
    private final SimpleItemEditContext context;

    public GlowGuiListener(SimpleItemEditContext context) {
        this.context = context;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        if (!(event.getInventory().getHolder() instanceof GlowGuiHolder holder)) {
            return;
        }

        event.setCancelled(true);
        int slot = event.getRawSlot();

        if (slot < 0 || slot >= event.getView().getTopInventory().getSize()) {
            return;
        }

        IItemEditor editor = context.editors().get("glow");

        if (!(editor instanceof GlowEditor glowEditor)) {
            return;
        }

        switch (slot) {
            case 11 -> setGlow(player, holder, glowEditor, true);
            case 15 -> setGlow(player, holder, glowEditor, false);
            case 22 -> back(player, holder);

            default -> {
            }
        }
    }

    private void setGlow(Player player, GlowGuiHolder holder, GlowEditor editor, boolean enabled) {
        GuiEditContext editorContext = new GuiEditContext(player, holder.session(), context.editors(),
                () -> new GlowGui(context, holder.session()),
                () -> new MainEditGui(holder.session(), context.editors()));

        editor.setGlow(editorContext, enabled);
        editorContext.refresh();
    }

    private void back(Player player, GlowGuiHolder holder) {
        new MainEditGui(holder.session(), context.editors()).open(player);
    }
}