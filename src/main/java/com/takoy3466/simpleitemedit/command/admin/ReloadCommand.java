package com.takoy3466.simpleitemedit.command.admin;

import com.takoy3466.simpleitemedit.SimpleItemEdit;
import com.takoy3466.simpleitemedit.command.ISimpleCommand;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements ISimpleCommand {

    private final SimpleItemEdit plugin;

    public ReloadCommand() {
        this.plugin = SimpleItemEdit.getInstance();
    }

    @Override
    public String name() {
        return "reload";
    }

    @Override
    public String description() {
        return "Reload the configuration.";
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        plugin.reloadConfig();

        sender.sendMessage("§aSimpleItemEdit configuration reloaded.");

        return true;
    }
}