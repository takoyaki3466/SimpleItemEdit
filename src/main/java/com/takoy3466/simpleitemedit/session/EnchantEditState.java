package com.takoy3466.simpleitemedit.session;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class EnchantEditState {

    private ItemStack applyBook;
    private ItemStack mergeFirst;
    private ItemStack mergeSecond;
    private ItemStack mergeResult;

    private ItemStack splitBook;

    private final List<ItemStack> splitResults = new ArrayList<>();

    private final List<ItemStack> consumedBooks = new ArrayList<>();

    public ItemStack applyBook() {
        return cloneOrNull(applyBook);
    }

    public void setApplyBook(ItemStack item) {
        this.applyBook = cloneOrNull(item);
    }

    public ItemStack mergeFirst() {
        return cloneOrNull(mergeFirst);
    }

    public void setMergeFirst(ItemStack item) {
        this.mergeFirst = cloneOrNull(item);
    }

    public ItemStack mergeSecond() {
        return cloneOrNull(mergeSecond);
    }

    public void setMergeSecond(ItemStack item) {
        this.mergeSecond = cloneOrNull(item);
    }

    public ItemStack mergeResult() {
        return cloneOrNull(mergeResult);
    }

    public void setMergeResult(ItemStack item) {
        this.mergeResult = cloneOrNull(item);
    }

    public void recordConsumedBook(ItemStack book) {
        if (isEmpty(book)) {
            return;
        }

        ItemStack copy = book.clone();
        copy.setAmount(1);

        consumedBooks.add(copy);
    }

    public List<ItemStack> consumedBooks() {
        List<ItemStack> result = new ArrayList<>();

        for (ItemStack item : consumedBooks) {
            result.add(item.clone());
        }

        return result;
    }

    public void refundConsumedBooks(Player player) {
        for (ItemStack book : consumedBooks) {
            giveOrDrop(player, book.clone());
        }

        consumedBooks.clear();
    }

    public void returnAllItems(Player player) {
        giveOrDrop(player, applyBook);
        giveOrDrop(player, mergeFirst);
        giveOrDrop(player, mergeSecond);
        giveOrDrop(player, mergeResult);

        applyBook = null;
        mergeFirst = null;
        mergeSecond = null;
        mergeResult = null;
    }

    public void clearInputs() {
        applyBook = null;
        mergeFirst = null;
        mergeSecond = null;
        mergeResult = null;
    }

    public void clearAll() {
        clearInputs();
        consumedBooks.clear();
    }

    public void saveApplyInventory(Inventory inventory, int slot) {
        applyBook = cloneOrNull(inventory.getItem(slot));
    }

    public void saveMergeInventory(Inventory inventory, int firstSlot, int secondSlot, int resultSlot) {
        mergeFirst = cloneOrNull(inventory.getItem(firstSlot));
        mergeSecond = cloneOrNull(inventory.getItem(secondSlot));
        mergeResult = cloneOrNull(inventory.getItem(resultSlot));
    }

    private void giveOrDrop(Player player, ItemStack item) {
        if (isEmpty(item)) {
            return;
        }

        player.getInventory().addItem(item).values().forEach(leftover -> player.getWorld().dropItemNaturally(player.getLocation(), leftover));
    }

    private static ItemStack cloneOrNull(ItemStack item) {
        return isEmpty(item) ? null : item.clone();
    }

    private static boolean isEmpty(ItemStack item) {
        return item == null || item.getType().isAir() || item.getAmount() <= 0;
    }

    public ItemStack splitBook() {
        return cloneOrNull(splitBook);
    }

    public void setSplitBook(ItemStack item) {
        this.splitBook = cloneOrNull(item);
    }

    public List<ItemStack> splitResults() {
        List<ItemStack> result = new ArrayList<>();

        for (ItemStack item : splitResults) {
            result.add(item.clone());
        }

        return result;
    }

    public void setSplitResults(List<ItemStack> results) {
        splitResults.clear();

        for (ItemStack item : results) {

            if (item == null || item.getType().isAir()) {
                continue;
            }

            splitResults.add(item.clone());
        }
    }

    public void returnSplitItems(Player player) {
        giveOrDrop(player, splitBook);

        for (ItemStack result : splitResults) {
            giveOrDrop(player, result);
        }

        splitBook = null;
        splitResults.clear();
    }

    public void removeSplitResult(int index) {
        if (index < 0 || index >= splitResults.size()) {
            return;
        }

        splitResults.remove(index);
    }
}