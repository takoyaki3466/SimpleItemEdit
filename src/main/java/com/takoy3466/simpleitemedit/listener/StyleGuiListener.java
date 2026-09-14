package com.takoy3466.simpleitemedit.listener;

import com.takoy3466.simpleitemedit.context.IEditContext;
import com.takoy3466.simpleitemedit.context.SimpleItemEditContext;
import com.takoy3466.simpleitemedit.editor.StyleEditor;
import com.takoy3466.simpleitemedit.gui.MainEditGui;
import com.takoy3466.simpleitemedit.holder.StyleGuiHolder;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

public class StyleGuiListener extends AbstractListener<StyleGuiHolder, StyleEditor> {
    public StyleGuiListener(SimpleItemEditContext context) {
        super(context, "style");
    }

    @EventHandler
    @Override
    protected void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        if (!(event.getInventory().getHolder() instanceof StyleGuiHolder holder)) {
            return;
        }

        event.setCancelled(true);
        int slot = event.getRawSlot();

        if (slot < 0 || slot >= event.getView().getTopInventory().getSize()) {
            return;
        }

        if (!(getEditor() instanceof StyleEditor styleEditor)) {
            return;
        }

        TextDecoration decoration = switch (slot) {
            case 10 -> TextDecoration.BOLD;
            case 11 -> TextDecoration.ITALIC;
            case 15 -> TextDecoration.UNDERLINED;
            case 16 -> TextDecoration.STRIKETHROUGH;
            case 20 -> TextDecoration.OBFUSCATED;
            default -> null;
        };

        if (decoration != null) {
            setStyle(player, holder, styleEditor, decoration);
            return;
        }

        switch (slot) {
            case 22 -> reset(player, holder, styleEditor);
            case 26 -> back(player, holder, styleEditor);
        }

    }

    private void setStyle(Player player, StyleGuiHolder holder, StyleEditor editor, TextDecoration decoration) {
        IEditContext editorContext = createContext(player, holder);

        boolean enabled = !editor.isEnabled(editorContext, decoration);

        editor.setStyle(editorContext, decoration, enabled);
        editorContext.refresh();
    }

    @Override
    protected void reset(@NotNull Player player, @NonNull StyleGuiHolder holder, @NonNull StyleEditor editor) {
        IEditContext editorContext = createContext(player, holder);
        editor.reset(editorContext);
    }

    @Override
    protected void back(@NotNull Player player, @NonNull StyleGuiHolder holder, @NonNull StyleEditor editor) {
        new MainEditGui(holder.session(), context.editors()).open(player);
    }
}