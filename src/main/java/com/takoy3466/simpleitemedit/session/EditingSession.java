package com.takoy3466.simpleitemedit.session;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class EditingSession {
    private final Player player;
    private final int sourceSlot;
    private final ItemStack originalItem;
    private ItemStack editingItem;

    public EditingSession(Player player, int sourceSlot, ItemStack originalItem) {
        this.player = player;
        this.sourceSlot = sourceSlot;
        this.originalItem = originalItem.clone();
        this.editingItem = originalItem.clone();
    }

    public Player player() {
        return player;
    }

    public int sourceSlot() {
        return sourceSlot;
    }

    public ItemStack originalItem() {
        return originalItem.clone();
    }

    public ItemStack editingItem() {
        return editingItem.clone();
    }

    public void setEditingItem(ItemStack item) {
        this.editingItem = item.clone();
    }

    public void reset() {
        this.editingItem = originalItem.clone();
    }

    public void apply() {
        player.getInventory().setItem(sourceSlot, editingItem.clone());
    }
}