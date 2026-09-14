package com.takoy3466.simpleitemedit.listener;

import com.takoy3466.simpleitemedit.context.GuiEditContext;
import com.takoy3466.simpleitemedit.context.SimpleItemEditContext;
import com.takoy3466.simpleitemedit.editor.GlowEditor;
import com.takoy3466.simpleitemedit.gui.GlowGui;
import com.takoy3466.simpleitemedit.gui.MainEditGui;
import com.takoy3466.simpleitemedit.holder.GlowGuiHolder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jspecify.annotations.NonNull;

public class GlowGuiListener extends AbstractListener<GlowGuiHolder, GlowEditor> {
    public GlowGuiListener(SimpleItemEditContext context) {
        super(context, "glow");
    }

    @EventHandler
    @Override
    protected void onClick(InventoryClickEvent event) {
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

        if (!(getEditor() instanceof GlowEditor glowEditor)) {
            return;
        }

        switch (slot) {
            case 11 -> setGlow(player, holder, glowEditor, true);
            case 15 -> setGlow(player, holder, glowEditor, false);
            case 22 -> back(player, holder, glowEditor);
            default -> {}
        }
    }

    private void setGlow(Player player, GlowGuiHolder holder, GlowEditor glowEditor, boolean enabled) {
        GuiEditContext editorContext = new GuiEditContext(player, holder.session(), context.editors(),
                () -> new GlowGui(context, holder.session()),
                () -> new MainEditGui(holder.session(), context.editors()));

        glowEditor.setGlow(editorContext, enabled);
        editorContext.refresh();
    }

    @Override
    protected void reset(@NonNull Player player, @NonNull GlowGuiHolder holder, @NonNull GlowEditor editor) {
        // リセットボタンなし
    }

    protected void back(@NonNull Player player, @NonNull GlowGuiHolder holder, @NonNull GlowEditor editor) {
        new MainEditGui(holder.session(), context.editors()).open(player);
    }
}