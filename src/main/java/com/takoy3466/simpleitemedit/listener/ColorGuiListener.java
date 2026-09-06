package com.takoy3466.simpleitemedit.listener;

import com.takoy3466.simpleitemedit.context.SimpleItemEditContext;
import com.takoy3466.simpleitemedit.editor.ColorEditor;
import com.takoy3466.simpleitemedit.context.GuiEditContext;
import com.takoy3466.simpleitemedit.context.IEditContext;
import com.takoy3466.simpleitemedit.editor.IItemEditor;
import com.takoy3466.simpleitemedit.gui.ColorGui;
import com.takoy3466.simpleitemedit.gui.MainEditGui;
import com.takoy3466.simpleitemedit.holder.ColorGuiHolder;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ColorGuiListener implements Listener {
    private final SimpleItemEditContext context;

    public ColorGuiListener(SimpleItemEditContext context) {
        this.context = context;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
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

        IItemEditor editor = context.editors().get("color");

        if (!(editor instanceof ColorEditor colorEditor)) {
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
            IEditContext editContext = createContext(player, holder);
            colorEditor.setColor(editContext, color);
            editContext.refresh();
            return;
        }

        if (slot == 49) {
            IEditContext editContext = createContext(player, holder);
            colorEditor.reset(editContext);
            return;
        }

        if (slot == 53) {
            new MainEditGui(holder.session(), context.editors()).open(player);
        }
    }

    private IEditContext createContext(Player player, ColorGuiHolder holder) {
        return new GuiEditContext(player, holder.session(), context.editors(),
                () -> new ColorGui(context, holder.session()),
                () -> new MainEditGui(holder.session(), context.editors()));
    }
}