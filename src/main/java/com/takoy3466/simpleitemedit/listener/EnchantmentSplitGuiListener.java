package com.takoy3466.simpleitemedit.listener;

import com.takoy3466.simpleitemedit.context.SimpleItemEditContext;
import com.takoy3466.simpleitemedit.gui.EnchantmentSplitGui;
import com.takoy3466.simpleitemedit.gui.MainEditGui;
import com.takoy3466.simpleitemedit.holder.EnchantmentSplitGuiHolder;
import com.takoy3466.simpleitemedit.session.EditingSession;
import com.takoy3466.simpleitemedit.util.EnchantBookSplitUtil;
import com.takoy3466.simpleitemedit.util.EnchantBookUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class EnchantmentSplitGuiListener implements Listener {
    private final SimpleItemEditContext context;

    public EnchantmentSplitGuiListener(SimpleItemEditContext context) {
        this.context = context;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!(event.getView().getTopInventory().getHolder() instanceof EnchantmentSplitGuiHolder holder)) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        int rawSlot = event.getRawSlot();
        int topSize = event.getView().getTopInventory().getSize();

        if (rawSlot >= topSize) {
            if (event.isShiftClick() || event.getClick() == ClickType.NUMBER_KEY || event.getClick() == ClickType.DOUBLE_CLICK || event.getClick() == ClickType.SWAP_OFFHAND || event.getClick() == ClickType.MIDDLE) {
                event.setCancelled(true);
            }

            return;
        }

        event.setCancelled(true);
        EditingSession session = holder.session();

        if (rawSlot >= EnchantmentSplitGuiHolder.RESULT_START_SLOT && rawSlot <= EnchantmentSplitGuiHolder.RESULT_END_SLOT) {
            takeResult(event, player, session, rawSlot);
            return;
        }

        switch (rawSlot) {
            case EnchantmentSplitGuiHolder.TARGET_BOOK_SLOT -> {
                handleTargetBook(event, player, session);
                saveState(event.getView().getTopInventory(), session);
            }

            case EnchantmentSplitGuiHolder.RESET_SLOT -> reset(player, session);
            case EnchantmentSplitGuiHolder.BACK_SLOT -> {
                saveState(event.getView().getTopInventory(), session);
                new MainEditGui(session, context.editors()).open(player);
            }
        }
    }

    private void takeResult(InventoryClickEvent event, Player player, EditingSession session, int slot) {
        Inventory inventory = event.getView().getTopInventory();
        ItemStack result = inventory.getItem(slot);

        if (result == null || result.getType().isAir()) {
            return;
        }

        ItemStack cursor = event.getCursor();

        if (!cursor.getType().isAir()) {
            return;
        }

        ItemStack targetBook = inventory.getItem(EnchantmentSplitGuiHolder.TARGET_BOOK_SLOT);

        if (targetBook == null || targetBook.getType().isAir()) {
            return;
        }

        targetBook.setAmount(targetBook.getAmount() - 1);

        if (targetBook.getAmount() <= 0) {
            inventory.setItem(EnchantmentSplitGuiHolder.TARGET_BOOK_SLOT, null);
        } else {
            inventory.setItem(EnchantmentSplitGuiHolder.TARGET_BOOK_SLOT, targetBook);
        }

        event.setCursor(result.clone());
        inventory.setItem(slot, null);

        int resultIndex = slot - EnchantmentSplitGuiHolder.RESULT_START_SLOT;

        session.enchantmentState().removeSplitResult(resultIndex);

        if (targetBook.getAmount() > 0) {
            List<ItemStack> results = session.enchantmentState().splitResults();
            clearResultSlots(inventory);
            int resultSlot = EnchantmentSplitGuiHolder.RESULT_START_SLOT;

            for (ItemStack item : results) {
                if (resultSlot > EnchantmentSplitGuiHolder.RESULT_END_SLOT) {
                    break;
                }

                inventory.setItem(resultSlot, item);
                resultSlot++;
            }

        } else {
            clearResults(inventory, session);
        }

        saveState(inventory, session);
    }

    private void handleTargetBook(InventoryClickEvent event, Player player, EditingSession session) {
        Inventory inventory = event.getView().getTopInventory();
        int slot = EnchantmentSplitGuiHolder.TARGET_BOOK_SLOT;
        ItemStack current = inventory.getItem(slot);
        ItemStack cursor = event.getCursor();

        if (event.getClick() == ClickType.RIGHT) {
            if (!EnchantBookUtil.isNotEnchantedBook(cursor)) {

                if (current == null || current.getType().isAir()) {
                    ItemStack one = cursor.clone();
                    one.setAmount(1);

                    inventory.setItem(slot, one);
                    cursor.setAmount(cursor.getAmount() - 1);

                    if (cursor.getAmount() <= 0) {
                        event.setCursor(null);
                    }

                    updateResults(inventory, session);

                    return;
                }
            }

            if (current != null && !current.getType().isAir() && (cursor == null || cursor.getType().isAir())) {
                ItemStack one = current.clone();
                one.setAmount(1);

                event.setCursor(one);
                current.setAmount(current.getAmount() - 1);

                if (current.getAmount() <= 0) {
                    inventory.setItem(slot, null);
                } else {
                    inventory.setItem(slot, current);
                }

                clearResults(inventory, session);

                return;
            }
        }

        if (event.getClick() == ClickType.LEFT) {
            if (cursor.getType().isAir()) {

                if (current != null && !current.getType().isAir()) {
                    event.setCursor(current.clone());

                    inventory.setItem(slot, null);
                    clearResults(inventory, session);
                }

                return;
            }

            if (!EnchantBookUtil.isNotEnchantedBook(cursor) && (current == null || current.getType().isAir())) {
                ItemStack one = cursor.clone();
                one.setAmount(1);

                inventory.setItem(slot, one);
                event.setCursor(null);

                updateResults(inventory, session);
            }
        }
    }

    private void updateResults(Inventory inventory, EditingSession session) {
        ItemStack book = inventory.getItem(EnchantmentSplitGuiHolder.TARGET_BOOK_SLOT);
        List<ItemStack> results = EnchantBookSplitUtil.split(book);

        clearResultSlots(inventory);

        for (int i = 0; i < results.size(); i++) {
            int slot = EnchantmentSplitGuiHolder.RESULT_START_SLOT + i;

            if (slot > EnchantmentSplitGuiHolder.RESULT_END_SLOT) {
                break;
            }

            inventory.setItem(slot, results.get(i));
        }

        session.enchantmentState().setSplitBook(book);
        session.enchantmentState().setSplitResults(results);
    }

    private void clearResults(Inventory inventory, EditingSession session) {
        clearResultSlots(inventory);
        session.enchantmentState().setSplitBook(null);
        session.enchantmentState().setSplitResults(List.of());
    }

    private void clearResultSlots(Inventory inventory) {
        for (int slot = EnchantmentSplitGuiHolder.RESULT_START_SLOT; slot <= EnchantmentSplitGuiHolder.RESULT_END_SLOT; slot++) {
            inventory.setItem(slot, null);
        }
    }

    private void reset(Player player, EditingSession session) {
        session.enchantmentState().setSplitBook(null);
        session.enchantmentState().setSplitResults(List.of());
        new EnchantmentSplitGui(context, session).open(player);
    }

    private void saveState(Inventory inventory, EditingSession session) {
        session.enchantmentState().setSplitBook(inventory.getItem(EnchantmentSplitGuiHolder.TARGET_BOOK_SLOT));
        List<ItemStack> results = new java.util.ArrayList<>();

        for (int slot = EnchantmentSplitGuiHolder.RESULT_START_SLOT; slot <= EnchantmentSplitGuiHolder.RESULT_END_SLOT; slot++) {
            ItemStack item = inventory.getItem(slot);

            if (item != null && !item.getType().isAir()) {
                results.add(item.clone());
            }
        }

        session.enchantmentState().setSplitResults(results);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (!(event.getInventory().getHolder() instanceof EnchantmentSplitGuiHolder holder)) {
            return;
        }

        saveState(event.getInventory(), holder.session());
    }

    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        if (!(event.getView().getTopInventory().getHolder() instanceof EnchantmentSplitGuiHolder)) {
            return;
        }

        int topSize = event.getView().getTopInventory().getSize();
        boolean touchesTop = event.getRawSlots().stream().anyMatch(slot -> slot < topSize);

        if (!touchesTop) {
            return;
        }

        event.setCancelled(true);
    }
}