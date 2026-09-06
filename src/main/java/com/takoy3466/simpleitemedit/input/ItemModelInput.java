package com.takoy3466.simpleitemedit.input;

import org.bukkit.entity.Player;

import java.util.Collection;

public class ItemModelInput {
    public static final String CANCEL = "cancel";

    public void start(Player player, Collection<String> completions) {

        player.addCustomChatCompletions(completions);

        player.sendMessage("§eEnter the Item Model ID in chat.");
        player.sendMessage("§7Example: §fminecraft:item/diamond");
        player.sendMessage("§7Type §fcancel §7to cancel.");
    }

    public void stop(Player player, Collection<String> completions) {
        player.removeCustomChatCompletions(completions);
    }
}