package com.takoy3466.simpleitemedit.listener;

import com.takoy3466.simpleitemedit.context.SimpleItemEditContext;
import com.takoy3466.simpleitemedit.context.GuiEditContext;
import com.takoy3466.simpleitemedit.context.IEditContext;
import com.takoy3466.simpleitemedit.editor.IItemEditor;
import com.takoy3466.simpleitemedit.editor.RarityEditor;
import com.takoy3466.simpleitemedit.gui.MainEditGui;
import com.takoy3466.simpleitemedit.gui.RarityGui;
import com.takoy3466.simpleitemedit.holder.RarityGuiHolder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemRarity;

public class RarityGuiListener implements Listener {
    private final SimpleItemEditContext context;

    public RarityGuiListener(SimpleItemEditContext context) {
        this.context = context;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        if (!(event.getInventory().getHolder() instanceof RarityGuiHolder holder)) {
            return;
        }

        event.setCancelled(true);

        int slot = event.getRawSlot();

        if (slot < 0 || slot >= event.getView().getTopInventory().getSize()) {
            return;
        }

        IItemEditor editor = context.editors().get("rarity");

        if (!(editor instanceof RarityEditor rarityEditor)) {
            return;
        }

        switch (slot) {
            case 10 -> setRarity(player, holder, rarityEditor, ItemRarity.COMMON);
            case 11 -> setRarity(player, holder, rarityEditor, ItemRarity.UNCOMMON);
            case 15 -> setRarity(player, holder, rarityEditor, ItemRarity.RARE);
            case 16 -> setRarity(player, holder, rarityEditor, ItemRarity.EPIC);
            case 22 -> back(player, holder);

            default -> {
            }
        }
    }

    private void setRarity(Player player, RarityGuiHolder holder, RarityEditor editor, ItemRarity rarity) {
        IEditContext editorContext =
                new GuiEditContext(player, holder.session(), context.editors(),
                        () -> new RarityGui(context, holder.session()),
                        () -> new MainEditGui(holder.session(), context.editors())
                );

        editor.setRarity(editorContext, rarity);
        editorContext.refresh();
    }

    private void back(Player player, RarityGuiHolder holder) {
        new MainEditGui(holder.session(), context.editors()).open(player);
    }
}