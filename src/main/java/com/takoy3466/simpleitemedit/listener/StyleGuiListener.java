package com.takoy3466.simpleitemedit.listener;

import com.takoy3466.simpleitemedit.context.SimpleItemEditContext;
import com.takoy3466.simpleitemedit.context.GuiEditContext;
import com.takoy3466.simpleitemedit.context.IEditContext;
import com.takoy3466.simpleitemedit.editor.IItemEditor;
import com.takoy3466.simpleitemedit.editor.StyleEditor;
import com.takoy3466.simpleitemedit.gui.MainEditGui;
import com.takoy3466.simpleitemedit.holder.StyleGuiHolder;
import com.takoy3466.simpleitemedit.gui.StyleGui;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class StyleGuiListener implements Listener {
    private final SimpleItemEditContext context;

    public StyleGuiListener(SimpleItemEditContext context) {
        this.context = context;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
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

        IItemEditor editor = context.editors().get("style");

        if (!(editor instanceof StyleEditor styleEditor)) {
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
            IEditContext editorContext = createContext(player, holder);

            boolean enabled = !styleEditor.isEnabled(editorContext, decoration);

            styleEditor.setStyle(editorContext, decoration, enabled);
            editorContext.refresh();

            return;
        }

        if (slot == 22) {
            IEditContext editorContext = createContext(player, holder);
            styleEditor.reset(editorContext);
            return;
        }

        if (slot == 26) {
            new MainEditGui(holder.session(), context.editors()).open(player);
        }
    }

    private IEditContext createContext(Player player, StyleGuiHolder holder) {
        return new GuiEditContext(player, holder.session(), context.editors(),
                () -> new StyleGui(context, holder.session()),
                () -> new MainEditGui(holder.session(), context.editors()));
    }
}