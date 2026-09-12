package com.takoy3466.simpleitemedit.listener;


import com.takoy3466.simpleitemedit.context.SimpleItemEditContext;
import com.takoy3466.simpleitemedit.gui.MainEditGui;
import com.takoy3466.simpleitemedit.holder.EnchantmentMergeGuiHolder;
import com.takoy3466.simpleitemedit.session.EditingSession;
import com.takoy3466.simpleitemedit.util.EnchantBookMergeUtil;
import com.takoy3466.simpleitemedit.util.EnchantBookUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class EnchantmentMergeGuiListener implements Listener {

    private final SimpleItemEditContext context;

    public EnchantmentMergeGuiListener(SimpleItemEditContext context) {
        this.context = context;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {

        if (!(event.getView().getTopInventory().getHolder() instanceof EnchantmentMergeGuiHolder holder)) {
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
            case EnchantmentMergeGuiHolder.FIRST_BOOK_SLOT -> {
                handleBookSlot(event, player, EnchantmentMergeGuiHolder.FIRST_BOOK_SLOT);
                updateResult(event.getView().getTopInventory());
                saveState(event.getView().getTopInventory(), session);
            }
            case EnchantmentMergeGuiHolder.SECOND_BOOK_SLOT -> {
                handleBookSlot(event, player, EnchantmentMergeGuiHolder.SECOND_BOOK_SLOT);
                updateResult(event.getView().getTopInventory());
                saveState(event.getView().getTopInventory(), session);
            }
            case EnchantmentMergeGuiHolder.RESULT_SLOT -> takeResult(event, player, session);
            case EnchantmentMergeGuiHolder.MERGE_BUTTON_SLOT -> merge(player, session);

            case EnchantmentMergeGuiHolder.BACK_SLOT -> {
                saveState(event.getView().getTopInventory(), session);
                new MainEditGui(session, context.editors()).open(player);
            }
        }

    }

    private void handleBookSlot(InventoryClickEvent event, Player player, int slot) {
        Inventory inventory = event.getView().getTopInventory();
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

            if (current != null && !current.getType().isAir() && (cursor == null || cursor.getType().isAir())) {
                ItemStack one = current.clone();

                one.setAmount(1);
                event.setCursor(one);
                current.setAmount(current.getAmount() - 1);

                if (current.getAmount() <= 0) {
                    inventory.setItem(slot, null);
                }

                return;
            }
        }

        if (event.getClick() == ClickType.LEFT) {
            if (cursor.getType().isAir()) {
                if (current != null && !current.getType().isAir()) {
                    event.setCursor(current.clone());
                    inventory.setItem(slot, null);
                }

                return;
            }

            if (!EnchantBookUtil.isNotEnchantedBook(cursor) && (current == null || current.getType().isAir())) {
                inventory.setItem(slot, cursor.clone());
                event.setCursor(null);
            }
        }
    }

    private void updateResult(Inventory inventory) {
        ItemStack first = inventory.getItem(EnchantmentMergeGuiHolder.FIRST_BOOK_SLOT);
        ItemStack second = inventory.getItem(EnchantmentMergeGuiHolder.SECOND_BOOK_SLOT);

        ItemStack result = EnchantBookMergeUtil.merge(first, second);
        inventory.setItem(EnchantmentMergeGuiHolder.RESULT_SLOT, result);
    }

    private void merge(Player player, EditingSession session) {
        EnchantmentMergeGuiHolder holder = getHolder(player);

        if (holder == null) {
            return;
        }

        Inventory inventory = holder.getInventory();

        ItemStack first = inventory.getItem(EnchantmentMergeGuiHolder.FIRST_BOOK_SLOT);
        ItemStack second = inventory.getItem(EnchantmentMergeGuiHolder.SECOND_BOOK_SLOT);

        ItemStack result = EnchantBookMergeUtil.merge(first, second);

        if (result == null) {
            player.sendMessage(Component.text("この2冊は合成できません。"));
            return;
        }

        inventory.setItem(EnchantmentMergeGuiHolder.FIRST_BOOK_SLOT, null);
        inventory.setItem(EnchantmentMergeGuiHolder.SECOND_BOOK_SLOT, null);
        inventory.setItem(EnchantmentMergeGuiHolder.RESULT_SLOT, result);

        saveState(inventory, session);
        player.sendMessage(Component.text("エンチャント本を合成しました。"));
    }

    private void takeResult(InventoryClickEvent event, Player player, EditingSession session) {
        Inventory inventory = event.getView().getTopInventory();
        ItemStack result = inventory.getItem(EnchantmentMergeGuiHolder.RESULT_SLOT);
        if (result == null || result.getType().isAir()) {
            return;
        }

        if (event.getCursor() != null && !event.getCursor().getType().isAir()) {
            return;
        }

        event.setCursor(result.clone());

        inventory.setItem(EnchantmentMergeGuiHolder.RESULT_SLOT, null);

        saveState(inventory, session);
    }

    private void saveState(Inventory inventory, EditingSession session) {
        session.enchantmentState().saveMergeInventory(inventory, EnchantmentMergeGuiHolder.FIRST_BOOK_SLOT, EnchantmentMergeGuiHolder.SECOND_BOOK_SLOT, EnchantmentMergeGuiHolder.RESULT_SLOT);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (!(event.getInventory().getHolder() instanceof EnchantmentMergeGuiHolder holder)) {
            return;
        }

        saveState(event.getInventory(), holder.session());
    }

    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        if (!(event.getView().getTopInventory().getHolder() instanceof EnchantmentMergeGuiHolder)) {
            return;
        }

        int topSize = event.getView().getTopInventory().getSize();
        boolean touchesTop = event.getRawSlots().stream().anyMatch(slot -> slot < topSize);

        if (!touchesTop) {
            return;
        }

        event.setCancelled(true);
    }

    private EnchantmentMergeGuiHolder getHolder(Player player) {
        if (player.getOpenInventory().getTopInventory().getHolder() instanceof EnchantmentMergeGuiHolder holder) {
            return holder;
        }

        return null;
    }
}