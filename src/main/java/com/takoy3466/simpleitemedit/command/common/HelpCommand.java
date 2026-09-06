package com.takoy3466.simpleitemedit.command.common;

import com.takoy3466.simpleitemedit.command.CommandRegistry;
import com.takoy3466.simpleitemedit.command.ISimpleCommand;
import org.bukkit.command.CommandSender;

public class HelpCommand implements ISimpleCommand {

    private final CommandRegistry registry;

    public HelpCommand(CommandRegistry registry) {
        this.registry = registry;
    }

    @Override
    public String name() {
        return "help";
    }

    @Override
    public String description() {
        return "Show available commands.";
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        sender.sendMessage("");
        sender.sendMessage("§6===== SimpleItemEdit =====");

        for (ISimpleCommand command : registry.getCommands()) {

            if (command.permission() != null && !sender.hasPermission(command.permission())) {
                continue;
            }

            sender.sendMessage("§e/simpleEdit " + command.name() + " §7- " + command.description());
        }

        sender.sendMessage("");

        return true;
    }
}