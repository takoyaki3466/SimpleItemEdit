package com.takoy3466.simpleitemedit.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractCommand implements CommandExecutor, TabCompleter {
    protected final CommandRegistry registry;

    public AbstractCommand(CommandRegistry registry) {
        this.registry = registry;
    }

    protected abstract void registerCommands();

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return registry.getCompletions(args[0]);
        }

        if (args.length >= 2) {
            ISimpleCommand iSimpleCommand = registry.get(args[0]);

            if (iSimpleCommand == null) {
                return List.of();
            }

            return iSimpleCommand.tabComplete(sender, Arrays.copyOfRange(args, 1, args.length));
        }

        return List.of();
    }
}
