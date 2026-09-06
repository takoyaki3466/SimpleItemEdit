package com.takoy3466.simpleitemedit.session;

import com.takoy3466.simpleitemedit.holder.AbstractGuiHolder;
import com.takoy3466.simpleitemedit.input.ChatInputHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.InventoryHolder;

import java.util.Objects;

public class EditSessionListener implements Listener {

    private final EditingSessionManager sessionManager;
    private final ChatInputHandler chatInputHandler;

    public EditSessionListener(EditingSessionManager sessionManager, ChatInputHandler chatInputHandler) {
        this.sessionManager = sessionManager;
        this.chatInputHandler = chatInputHandler;
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player player)) {
            return;
        }

        if (!(event.getInventory().getHolder() instanceof AbstractGuiHolder)) {
            return;
        }

        Bukkit.getScheduler().runTask(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("SimpleItemEdit")), () -> {
            if (chatInputHandler.isWaiting(player)) {
                return;
            }

            InventoryHolder currentHolder = player.getOpenInventory().getTopInventory().getHolder();

            if (currentHolder instanceof AbstractGuiHolder) {
                return;
            }

            sessionManager.remove(player);});
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        chatInputHandler.cancel(player);
        sessionManager.remove(player);
    }
}