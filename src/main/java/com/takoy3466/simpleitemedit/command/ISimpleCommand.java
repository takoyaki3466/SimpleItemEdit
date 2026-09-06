package com.takoy3466.simpleitemedit.command;

import org.bukkit.command.CommandSender;

import java.util.List;

public interface ISimpleCommand {

    String name();

    String description();

    default String permission() {
        return null;
    }

    boolean execute(CommandSender sender, String[] args);

    default List<String> tabComplete(CommandSender sender, String[] args) {
        return List.of();
    }
}
