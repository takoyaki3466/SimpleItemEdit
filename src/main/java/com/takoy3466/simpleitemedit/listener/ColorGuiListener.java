package com.takoy3466.simpleitemedit.listener;

import com.takoy3466.simpleitemedit.context.IEditContext;
import com.takoy3466.simpleitemedit.context.SimpleItemEditContext;
import com.takoy3466.simpleitemedit.editor.ColorEditor;
import com.takoy3466.simpleitemedit.gui.MainEditGui;
import com.takoy3466.simpleitemedit.holder.ColorGuiHolder;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jspecify.annotations.NonNull;

public class ColorGuiListener extends AbstractListener<ColorGuiHolder, ColorEditor> {
    public ColorGuiListener(SimpleItemEditContext context) {
        super(context, "color");
    }

    @EventHandler
    @Override
    protected void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        if (!(event.getInventory().getHolder() instanceof ColorGuiHolder holder)) {
            return;
        }

        event.setCancelled(true);
        int slot = event.getRawSlot();

        if (slot < 0 || slot >= event.getView().getTopInventory().getSize()) {
            return;
        }

        if (!(getEditor() instanceof ColorEditor colorEditor)) {
            return;
        }

        NamedTextColor color = switch (slot) {
            case 27 -> NamedTextColor.WHITE;
            case 28 -> NamedTextColor.GRAY;
            case 29 -> NamedTextColor.DARK_GRAY;
            case 30 -> NamedTextColor.BLACK;
            case 31 -> NamedTextColor.RED;
            case 32 -> NamedTextColor.DARK_RED;
            case 33 -> NamedTextColor.GOLD;
            case 34 -> NamedTextColor.YELLOW;
            case 35 -> NamedTextColor.GREEN;
            case 36 -> NamedTextColor.DARK_GREEN;
            case 37 -> NamedTextColor.AQUA;
            case 38 -> NamedTextColor.DARK_AQUA;
            case 39 -> NamedTextColor.BLUE;
            case 40 -> NamedTextColor.DARK_BLUE;
            case 41 -> NamedTextColor.LIGHT_PURPLE;
            case 42 -> NamedTextColor.DARK_PURPLE;
            default -> null;
        };

        if (color != null) {
            setColor(player, holder, colorEditor, color);
            return;
        }

        switch (slot) {
            case 49 -> reset(player, holder, colorEditor);
            case 53 -> back(player, holder, colorEditor);
            default -> {}
        }
    }

    private void setColor(Player player, ColorGuiHolder holder, ColorEditor editor, NamedTextColor color) {
        IEditContext editContext = createContext(player, holder);
        editor.setColor(editContext, color);
        editContext.refresh();
    }

    @Override
    protected void reset(@NonNull Player player, @NonNull ColorGuiHolder holder, @NonNull ColorEditor editor) {
        IEditContext editContext = createContext(player, holder);
        editor.reset(editContext);
    }

    @Override
    protected void back(@NonNull Player player, @NonNull ColorGuiHolder holder, @NonNull ColorEditor editor) {
        new MainEditGui(holder.session(), context.editors()).open(player);
    }
}