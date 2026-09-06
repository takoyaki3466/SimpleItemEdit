package com.takoy3466.simpleitemedit.input;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;

import java.util.*;
import java.util.function.Consumer;

public class ChatInputHandler implements IInputHandler, Listener {
    private final Map<UUID, Consumer<String>> callbacks = new HashMap<>();
    private final Map<UUID, Collection<String>> completions = new HashMap<>();
    private final Plugin plugin;

    public ChatInputHandler(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public String id() {
        return "chat";
    }

    @Override
    public void start(Player player, Consumer<String> callback) {
        start(player, java.util.List.of(), callback);
    }

    @Override
    public void start(Player player, Collection<String> completions, Consumer<String> callback) {
        UUID uuid = player.getUniqueId();
        callbacks.put(uuid, callback);
        Collection<String> copied = List.copyOf(completions);
        this.completions.put(uuid, copied);

        player.setCustomChatCompletions(copied);

        player.sendMessage("§e入力してください。");
        player.sendMessage("§7Tabキーで候補を表示できます。");
        player.sendMessage("§7キャンセルする場合は「cancel」と入力してください。");
    }

    @Override
    public void cancel(Player player) {
        UUID uuid = player.getUniqueId();
        callbacks.remove(uuid);
        Collection<String> oldCompletions = completions.remove(uuid);

        if (oldCompletions != null) {
            player.removeCustomChatCompletions(oldCompletions);
        }

        player.sendMessage("§7入力をキャンセルしました。");
    }

    @Override
    public boolean isWaiting(Player player) {
        return callbacks.containsKey(player.getUniqueId());
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        Consumer<String> callback = callbacks.remove(uuid);

        if (callback == null) {
            return;
        }

        event.setCancelled(true);
        Collection<String> oldCompletions = completions.remove(uuid);

        if (oldCompletions != null) {
            Bukkit.getScheduler().runTask(plugin, () -> player.removeCustomChatCompletions(oldCompletions));
        }

        String input = event.getMessage();

        if (input.equalsIgnoreCase("cancel")) {
            Bukkit.getScheduler().runTask(plugin, () -> player.sendMessage("§7入力をキャンセルしました。"));
            return;
        }

        callback.accept(input);
    }
}