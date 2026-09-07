package com.takoy3466.simpleitemedit.listener;

import com.takoy3466.simpleitemedit.context.GuiEditContext;
import com.takoy3466.simpleitemedit.context.IEditContext;
import com.takoy3466.simpleitemedit.context.SimpleItemEditContext;
import com.takoy3466.simpleitemedit.editor.EnchantmentEditor;
import com.takoy3466.simpleitemedit.gui.EnchantmentGui;
import com.takoy3466.simpleitemedit.gui.MainEditGui;
import com.takoy3466.simpleitemedit.holder.EnchantmentGuiHolder;
import com.takoy3466.simpleitemedit.util.EnchantBookMergeUtil;
import com.takoy3466.simpleitemedit.util.EnchantBookUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class EnchantmentGuiListener implements Listener {
    private final SimpleItemEditContext context;

    public EnchantmentGuiListener(SimpleItemEditContext context) {
        this.context = context;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        if (!(event.getInventory().getHolder() instanceof EnchantmentGuiHolder holder)) {
            return;
        }

        int topSize = event.getView().getTopInventory().getSize();

        int rawSlot = event.getRawSlot();

        if (rawSlot >= topSize) {

            if (event.isShiftClick()) {
                event.setCancelled(true);
                return;
            }

            if (event.getClick() == ClickType.DOUBLE_CLICK || event.getClick() == ClickType.NUMBER_KEY || event.getClick() == ClickType.SWAP_OFFHAND || event.getClick() == ClickType.MIDDLE) {
                event.setCancelled(true);
                return;
            }

            return;
        }

        event.setCancelled(true);

        if (!isNormalClick(event.getClick())) {
            return;
        }


        if (isApplyBookSlot(rawSlot)) {
            handleInputSlotClick(player, holder, rawSlot, event);
            return;
        }

        if (isMergeInputSlot(rawSlot)) {
            handleInputSlotClick(player, holder, rawSlot, event);
        }

        switch (rawSlot) {
            case 31 -> applyEnchantment(player, holder);
            case 49 -> mergeBooks(player, holder);
            case 40 -> takeResult(player, holder);
            case 48 -> reset(player, holder);
            case 53 -> back(player, holder);
        }

    }

    private boolean isNormalClick(ClickType click) {
        return click == ClickType.LEFT || click == ClickType.RIGHT;
    }

    private boolean isApplyBookSlot(int slot) {
        return slot == EnchantmentGuiHolder.APPLY_BOOK_SLOT;
    }

    private boolean isMergeInputSlot(int slot) {
        return slot == EnchantmentGuiHolder.MERGE_FIRST_SLOT || slot == EnchantmentGuiHolder.MERGE_SECOND_SLOT;
    }

    private void handleInputSlotClick(Player player, EnchantmentGuiHolder holder, int slot, InventoryClickEvent event) {
        Inventory inventory = holder.getInventory();

        ItemStack current = inventory.getItem(slot);
        ItemStack cursor = event.getCursor();

        boolean cursorEmpty = isEmpty(cursor);
        boolean currentEmpty = isEmpty(current);

        if (currentEmpty) {
            if (cursorEmpty) {
                return;
            }

            if (!isValidBook(cursor)) {
                player.sendMessage("§cここにはエンチャント本しか置けません。");
                return;
            }

            if (event.getClick() == ClickType.RIGHT) {
                ItemStack placed = cursor.clone();
                placed.setAmount(1);
                inventory.setItem(slot, placed);
                cursor.setAmount(cursor.getAmount() - 1);
                event.setCursor(cursor.getAmount() <= 0 ? null : cursor);

            } else {
                inventory.setItem(slot, cursor.clone());
                event.setCursor(null);
            }

            updateResult(holder);

            return;
        }

        if (!isValidBook(current)) {
            inventory.setItem(slot, null);

            giveOrDrop(player, current);

            return;
        }

        if (cursorEmpty) {

            if (event.getClick() == ClickType.RIGHT) {
                ItemStack taken = current.clone();
                taken.setAmount(1);
                event.setCursor(taken);
                EnchantBookUtil.consumeOne(current);
                inventory.setItem(slot, isEmpty(current) ? null : current);

            } else {
                event.setCursor(current.clone());
                inventory.setItem(slot, null);
            }

            updateResult(holder);

            return;
        }

        if (!isValidBook(cursor)) {
            player.sendMessage("§cここにはエンチャント本しか置けません。");
            return;
        }

        if (current.isSimilar(cursor)) {
            int max = current.getMaxStackSize();
            int space = max - current.getAmount();

            if (space <= 0) {
                return;
            }

            int move = Math.min(space, cursor.getAmount());

            current.setAmount(current.getAmount() + move);
            cursor.setAmount(cursor.getAmount() - move);

            inventory.setItem(slot, current);
            event.setCursor(cursor.getAmount() <= 0 ? null : cursor);

            updateResult(holder);

            return;
        }

        inventory.setItem(slot, cursor.clone());
        event.setCursor(current.clone());
        updateResult(holder);
    }

    private void applyEnchantment(Player player, EnchantmentGuiHolder holder) {
        Inventory inventory = holder.getInventory();
        ItemStack book = inventory.getItem(EnchantmentGuiHolder.APPLY_BOOK_SLOT);

        if (!isValidBook(book)) {
            player.sendMessage("§cエンチャント本を置いてください。");
            return;
        }

        if (EnchantBookUtil.hasNotSingleEnchantment(book)) {
            player.sendMessage("§c1種類のエンチャントだけを持つ本を使用してください。");
            return;
        }

        EnchantmentEditor editor = getEnchantmentEditor();

        if (editor == null) {
            return;
        }

        IEditContext editorContext = createEditorContext(player, holder);
        boolean success = editor.applyBook(editorContext, book);

        if (!success) {
            player.sendMessage("§cこのエンチャント本は使用できません。");
            return;
        }

        EnchantBookUtil.consumeOne(book);

        inventory.setItem(EnchantmentGuiHolder.APPLY_BOOK_SLOT, isEmpty(book) ? null : book);
        inventory.setItem(EnchantmentGuiHolder.TARGET_SLOT, holder.session().editingItem());

        player.sendMessage("§aエンチャントを適用しました。");
    }

    private void mergeBooks(Player player, EnchantmentGuiHolder holder) {
        Inventory inventory = holder.getInventory();

        ItemStack first = inventory.getItem(EnchantmentGuiHolder.MERGE_FIRST_SLOT);
        ItemStack second = inventory.getItem(EnchantmentGuiHolder.MERGE_SECOND_SLOT);

        if (!EnchantBookMergeUtil.canMerge(first, second)) {
            player.sendMessage("§cこの2冊は合成できません。");
            return;
        }

        ItemStack result = EnchantBookMergeUtil.merge(first, second);

        if (result == null) {
            return;
        }

        ItemStack existingResult = inventory.getItem(EnchantmentGuiHolder.MERGE_RESULT_SLOT);

        if (!isEmpty(existingResult)) {
            player.sendMessage("§c先に結果の本を取り出してください。");
            return;
        }

        EnchantBookUtil.consumeOne(first);
        EnchantBookUtil.consumeOne(second);

        inventory.setItem(EnchantmentGuiHolder.MERGE_FIRST_SLOT, isEmpty(first) ? null : first);
        inventory.setItem(EnchantmentGuiHolder.MERGE_SECOND_SLOT, isEmpty(second) ? null : second);
        inventory.setItem(EnchantmentGuiHolder.MERGE_RESULT_SLOT, result);

        player.sendMessage("§aエンチャント本を合成しました。");
    }

    private void takeResult(Player player, EnchantmentGuiHolder holder) {
        Inventory inventory = holder.getInventory();
        ItemStack result = inventory.getItem(EnchantmentGuiHolder.MERGE_RESULT_SLOT);

        if (isEmpty(result)) {
            return;
        }

        ItemStack cursor = player.getItemOnCursor();

        if (!isEmpty(cursor)) {
            player.sendMessage("§cカーソルを空にしてください。");
            return;
        }

        player.setItemOnCursor(result.clone());
        inventory.setItem(EnchantmentGuiHolder.MERGE_RESULT_SLOT, null);
    }

    private void reset(Player player, EnchantmentGuiHolder holder) {
        EnchantmentEditor editor = getEnchantmentEditor();

        if (editor == null) {
            return;
        }

        IEditContext editorContext = createEditorContext(player, holder);
        editor.reset(editorContext);

        Inventory inventory = holder.getInventory();
        inventory.setItem(EnchantmentGuiHolder.TARGET_SLOT, holder.session().editingItem());
    }

    private void back(Player player, EnchantmentGuiHolder holder) {
        returnItems(player, holder.getInventory());

        new MainEditGui(holder.session(), context.editors()).open(player);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player player)) {
            return;
        }

        if (!(event.getInventory().getHolder() instanceof EnchantmentGuiHolder)) {
            return;
        }

        returnItems(player, event.getInventory());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryDrag(InventoryDragEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        if (!(event.getInventory().getHolder() instanceof EnchantmentGuiHolder holder)) {
            return;
        }

        event.setCancelled(true);

        for (int rawSlot : event.getRawSlots()) {
            if (rawSlot >= event.getView().getTopInventory().getSize()) {
                continue;
            }

            if (!isInputSlot(rawSlot)) {
                return;
            }
        }

        ItemStack oldCursor = event.getOldCursor();

        if (!isValidBook(oldCursor)) {
            player.sendMessage("§cここにはエンチャント本しか置けません。");
            return;
        }

        int targetSlot = -1;

        for (int rawSlot : event.getRawSlots()) {
            if (isInputSlot(rawSlot)) {
                targetSlot = rawSlot;
                break;
            }
        }

        if (targetSlot == -1) {
            return;
        }

        Inventory inventory = holder.getInventory();
        ItemStack current = inventory.getItem(targetSlot);

        if (!isEmpty(current)) {
            return;
        }

        ItemStack placed = oldCursor.clone();
        inventory.setItem(targetSlot, placed);
        player.setItemOnCursor(null);

        updateResult(holder);
    }

    private boolean isInputSlot(int slot) {
        return slot == EnchantmentGuiHolder.APPLY_BOOK_SLOT || slot == EnchantmentGuiHolder.MERGE_FIRST_SLOT || slot == EnchantmentGuiHolder.MERGE_SECOND_SLOT;
    }

    private boolean isValidBook(ItemStack item) {
        return !EnchantBookUtil.isNotEnchantedBook(item);
    }

    private boolean isEmpty(ItemStack item) {
        return item == null || item.getType().isAir() || item.getAmount() <= 0;
    }

    private void updateResult(EnchantmentGuiHolder holder) {

    }

    private void returnItems(Player player, Inventory inventory) {
        returnItem(player, inventory, EnchantmentGuiHolder.APPLY_BOOK_SLOT);

        returnItem(player, inventory, EnchantmentGuiHolder.MERGE_FIRST_SLOT);

        returnItem(player, inventory, EnchantmentGuiHolder.MERGE_SECOND_SLOT);

        returnItem(player, inventory, EnchantmentGuiHolder.MERGE_RESULT_SLOT);
    }

    private void returnItem(Player player, Inventory inventory, int slot) {
        ItemStack item = inventory.getItem(slot);

        if (isEmpty(item)) {
            return;
        }

        inventory.setItem(slot, null);

        giveOrDrop(player, item);
    }

    private void giveOrDrop(Player player, ItemStack item) {
        if (isEmpty(item)) {
            return;
        }

        player.getInventory().addItem(item).values().forEach(leftover -> player.getWorld().dropItemNaturally(player.getLocation(), leftover));
    }

    private EnchantmentEditor getEnchantmentEditor() {
        if (!(context.editors().get("enchantment") instanceof EnchantmentEditor editor)) {
            return null;
        }

        return editor;
    }

    private IEditContext createEditorContext(Player player, EnchantmentGuiHolder holder) {
        return new GuiEditContext(player, holder.session(), context.editors(),
                () -> new EnchantmentGui(context, holder.session()),
                () -> new MainEditGui(holder.session(), context.editors()));
    }
}