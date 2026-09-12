package com.takoy3466.simpleitemedit.listener;

import com.takoy3466.simpleitemedit.context.SimpleItemEditContext;
import com.takoy3466.simpleitemedit.gui.EnchantmentApplyGui;
import com.takoy3466.simpleitemedit.gui.MainEditGui;
import com.takoy3466.simpleitemedit.holder.EnchantmentApplyGuiHolder;
import com.takoy3466.simpleitemedit.session.EditingSession;
import com.takoy3466.simpleitemedit.session.EnchantEditState;
import com.takoy3466.simpleitemedit.util.EnchantBookUtil;
import com.takoy3466.simpleitemedit.util.EnchantmentUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class EnchantmentApplyGuiListener implements Listener {
    private final SimpleItemEditContext context;

    public EnchantmentApplyGuiListener(SimpleItemEditContext context) {
        this.context = context;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!(event.getView().getTopInventory().getHolder() instanceof EnchantmentApplyGuiHolder holder)) {
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

        switch (rawSlot) {
            case EnchantmentApplyGuiHolder.BOOK_SLOT -> handleBookSlot(event, player, session);
            case EnchantmentApplyGuiHolder.APPLY_BUTTON_SLOT -> applyEnchantment(player, session);
            case EnchantmentApplyGuiHolder.RESET_SLOT -> reset(player, session);

            case EnchantmentApplyGuiHolder.BACK_SLOT -> {
                saveState(event.getView().getTopInventory(), session);
                new MainEditGui(session, context.editors()).open(player);

            }
        }

    }

    /**
     * エンチャント本スロットの処理。
     */
    private void handleBookSlot(InventoryClickEvent event, Player player, EditingSession session) {
        Inventory inventory = event.getView().getTopInventory();

        int slot = EnchantmentApplyGuiHolder.BOOK_SLOT;

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

                    return;
                }
            }

            if (current != null && !current.getType().isAir()) {
                if (event.getCursor() == null || event.getCursor().getType().isAir()) {
                    ItemStack one = current.clone();
                    one.setAmount(1);

                    event.setCursor(one);
                    current.setAmount(current.getAmount() - 1);

                    if (current.getAmount() <= 0) {
                        inventory.setItem(slot, null);
                    } else {
                        inventory.setItem(slot, current);
                    }
                }

                return;
            }

            return;
        }

        if (event.getClick() == ClickType.LEFT) {
            if (cursor == null || cursor.getType().isAir()) {
                if (current != null && !current.getType().isAir()) {
                    event.setCursor(current.clone());
                    inventory.setItem(slot, null);
                }

                return;
            }

            if (!EnchantBookUtil.isNotEnchantedBook(cursor)) {
                if (current == null || current.getType().isAir()) {
                    inventory.setItem(slot, cursor.clone());
                    event.setCursor(null);
                }
            }
        }
    }

    private void applyEnchantment(Player player, EditingSession session) {
        EnchantEditState state = session.enchantmentState();
        ItemStack book = state.applyBook();

        if (EnchantBookUtil.isNotEnchantedBook(book)) {

            player.sendMessage(Component.text("エンチャント本を入れてください。"));

            return;
        }

        if (EnchantBookUtil.hasNotSingleEnchantment(book)) {

            player.sendMessage(Component.text("エンチャントが1種類だけ付いた本を使用してください。"));

            return;
        }

        Enchantment enchantment = EnchantBookUtil.getSingleEnchantment(book);
        int bookLevel = EnchantBookUtil.getSingleLevel(book);
        ItemStack editing = session.editingItem();
        int currentLevel = EnchantmentUtil.getLevel(editing, enchantment);

        if (!EnchantmentUtil.canApply(currentLevel, bookLevel)) {
            player.sendMessage(Component.text("このエンチャント本は使用できません。"));
            return;
        }

        int newLevel = EnchantmentUtil.calculateNewLevel(currentLevel, bookLevel);

        EnchantmentUtil.apply(editing, enchantment, newLevel);
        session.setEditingItem(editing);

        state.recordConsumedBook(book);

        EnchantBookUtil.consumeOne(book);

        if (book.getAmount() <= 0) {
            state.setApplyBook(null);
        } else {
            state.setApplyBook(book);
        }

        player.sendMessage(Component.text("エンチャントを適用しました。"));

        new EnchantmentApplyGui(context, session).open(player);
    }

    private void reset(Player player, EditingSession session) {
        ItemStack original = session.originalItem();
        ItemStack editing = session.editingItem();

        for (Enchantment enchantment : editing.getEnchantments().keySet()) {
            editing.removeEnchantment(enchantment);
        }

        for (Map.Entry<Enchantment, Integer> entry : original.getEnchantments().entrySet()) {
            EnchantmentUtil.apply(editing, entry.getKey(), entry.getValue());
        }

        session.setEditingItem(editing);
        session.enchantmentState().refundConsumedBooks(player);
        session.enchantmentState().returnAllItems(player);

        new EnchantmentApplyGui(context, session).open(player);
    }

    private void saveState(Inventory inventory, EditingSession session) {
        session.enchantmentState().saveApplyInventory(inventory, EnchantmentApplyGuiHolder.BOOK_SLOT);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {

        if (!(event.getInventory().getHolder() instanceof EnchantmentApplyGuiHolder holder)) {
            return;
        }

        saveState(event.getInventory(), holder.session());
    }

    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        if (!(event.getView().getTopInventory().getHolder() instanceof EnchantmentApplyGuiHolder)) {
            return;
        }

        int topSize = event.getView().getTopInventory().getSize();

        boolean touchesTop = event.getRawSlots().stream().anyMatch(slot -> slot < topSize);

        if (!touchesTop) {
            return;
        }

        if (event.getRawSlots().size() == 1 && event.getRawSlots().contains(EnchantmentApplyGuiHolder.BOOK_SLOT) && !EnchantBookUtil.isNotEnchantedBook(event.getOldCursor())) {
            return;
        }

        event.setCancelled(true);
    }
}