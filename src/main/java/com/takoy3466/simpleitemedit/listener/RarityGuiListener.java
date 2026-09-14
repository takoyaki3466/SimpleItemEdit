package com.takoy3466.simpleitemedit.listener;

import com.takoy3466.simpleitemedit.context.IEditContext;
import com.takoy3466.simpleitemedit.context.SimpleItemEditContext;
import com.takoy3466.simpleitemedit.editor.RarityEditor;
import com.takoy3466.simpleitemedit.gui.MainEditGui;
import com.takoy3466.simpleitemedit.holder.RarityGuiHolder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemRarity;
import org.jspecify.annotations.NonNull;

public class RarityGuiListener extends AbstractListener<RarityGuiHolder, RarityEditor> {
    public RarityGuiListener(SimpleItemEditContext context) {
        super(context, "rarity");
    }

    @EventHandler
    @Override
    public void onClick(InventoryClickEvent event) {
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

        if (!(getEditor() instanceof RarityEditor rarityEditor)) {
            return;
        }

        switch (slot) {
            case 10 -> setRarity(player, holder, rarityEditor, ItemRarity.COMMON);
            case 11 -> setRarity(player, holder, rarityEditor, ItemRarity.UNCOMMON);
            case 15 -> setRarity(player, holder, rarityEditor, ItemRarity.RARE);
            case 16 -> setRarity(player, holder, rarityEditor, ItemRarity.EPIC);
            case 22 -> back(player, holder, rarityEditor);
            default -> {}
        }
    }

    private void setRarity(Player player, RarityGuiHolder holder, RarityEditor editor, ItemRarity rarity) {
        IEditContext editorContext = createContext(player, holder);
        editor.setRarity(editorContext, rarity);
        editorContext.refresh();
    }

    @Override
    public void reset(@NonNull Player player, @NonNull RarityGuiHolder holder, @NonNull RarityEditor editor) {
        // リセットボタンなし
    }

    protected void back(@NonNull Player player, @NonNull RarityGuiHolder holder, @NonNull RarityEditor editor) {
        new MainEditGui(holder.session(), context.editors()).open(player);
    }
}