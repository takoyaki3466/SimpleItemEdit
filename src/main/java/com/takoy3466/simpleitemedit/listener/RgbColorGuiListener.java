package com.takoy3466.simpleitemedit.listener;

import com.takoy3466.simpleitemedit.context.GuiEditContext;
import com.takoy3466.simpleitemedit.context.IEditContext;
import com.takoy3466.simpleitemedit.context.SimpleItemEditContext;
import com.takoy3466.simpleitemedit.editor.RgbColorEditor;
import com.takoy3466.simpleitemedit.gui.MainEditGui;
import com.takoy3466.simpleitemedit.gui.RgbColorGui;
import com.takoy3466.simpleitemedit.holder.RgbColorGuiHolder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jspecify.annotations.NonNull;

public class RgbColorGuiListener extends AbstractListener<RgbColorGuiHolder, RgbColorEditor> {
    public RgbColorGuiListener(SimpleItemEditContext context) {
        super(context, "rgb_color");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    @Override
    public void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        if (!(event.getInventory().getHolder() instanceof RgbColorGuiHolder holder)) {
            return;
        }

        event.setCancelled(true);
        int slot = event.getRawSlot();

        if (slot < 0 || slot >= event.getView().getTopInventory().getSize()) {
            return;
        }

        if (!(getEditor() instanceof RgbColorEditor rgbColorEditor)) {
            return;
        }

        switch (slot) {
            case 28 -> holder.red(holder.red() - 10);
            case 29 -> holder.red(holder.red() - 1);
            case 32 -> holder.red(holder.red() + 1);
            case 33 -> holder.red(holder.red() + 10);
            case 37 -> holder.green(holder.green() - 10);
            case 38 -> holder.green(holder.green() - 1);
            case 41 -> holder.green(holder.green() + 1);
            case 42 -> holder.green(holder.green() + 10);
            case 46 -> holder.blue(holder.blue() - 10);
            case 47 -> holder.blue(holder.blue() - 1);
            case 50 -> holder.blue(holder.blue() + 1);
            case 51 -> holder.blue(holder.blue() + 10);
            case 53 -> back(player, holder, rgbColorEditor);
            default -> {}
        }

        applyColor(holder, rgbColorEditor);

        new RgbColorGui(context, holder.session()).open(player);
    }

    private void applyColor(RgbColorGuiHolder holder, RgbColorEditor rgbColorEditor) {
        IEditContext editorContext = new GuiEditContext(holder.session().player(), holder.session(), context.editors(), () -> new RgbColorGui(context, holder.session()), () -> new MainEditGui(holder.session(), context.editors()));
        rgbColorEditor.setColor(editorContext, holder.red(), holder.green(), holder.blue());
    }

    @Override
    protected void reset(@NonNull Player player, @NonNull RgbColorGuiHolder holder, @NonNull RgbColorEditor editor) {
        // リセットボタンなし
    }

    @Override
    protected void back(@NonNull Player player, @NonNull RgbColorGuiHolder holder, @NonNull RgbColorEditor editor) {
        new MainEditGui(holder.session(), context.editors()).open(player);
    }


}