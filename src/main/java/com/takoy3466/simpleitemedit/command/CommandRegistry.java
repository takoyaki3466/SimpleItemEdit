package com.takoy3466.simpleitemedit.command;

import org.bukkit.command.CommandSender;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CommandRegistry {

    private final Map<String, ISimpleCommand> commands =
            new LinkedHashMap<>();

    public void register(ISimpleCommand command) {
        String name = command.name().toLowerCase();

        if (commands.containsKey(name)) {
            throw new IllegalStateException("Command already registered: " + name);
        }

        commands.put(name, command);
    }

    public void unregister(String name) {
        commands.remove(name.toLowerCase());
    }

    public ISimpleCommand get(String name) {
        if (name == null) {
            return null;
        }

        return commands.get(name.toLowerCase());
    }

    public boolean contains(String name) {
        return get(name) != null;
    }

    public Collection<ISimpleCommand> getCommands() {
        return commands.values();
    }

    public List<String> getCompletions(String input) {
        String lowerInput = input.toLowerCase();

        return commands.values().stream().map(ISimpleCommand::name).filter(name -> name.toLowerCase().startsWith(lowerInput)).toList();
    }

    public boolean execute(CommandSender sender, String name, String[] args) {
        ISimpleCommand command = get(name);

        if (command == null) {
            return false;
        }

        String permission = command.permission();

        if (permission != null && !sender.hasPermission(permission)) {
            sender.sendMessage("§cYou do not have permission to use this command.");

            return true;
        }

        return command.execute(sender, args);
    }
}