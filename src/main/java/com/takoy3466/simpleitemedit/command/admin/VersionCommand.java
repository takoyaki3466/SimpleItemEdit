package com.takoy3466.simpleitemedit.command.admin;


import com.takoy3466.simpleitemedit.SimpleItemEdit;
import com.takoy3466.simpleitemedit.command.ISimpleCommand;
import org.bukkit.command.CommandSender;

public class VersionCommand implements ISimpleCommand {

    private final SimpleItemEdit plugin;

    public VersionCommand() {
        this.plugin = SimpleItemEdit.getInstance();
    }

    @Override
    public String name() {
        return "version";
    }

    @Override
    public String description() {
        return "Show the plugin version.";
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        sender.sendMessage("§aSimpleItemEdit version: §f" + plugin.getDescription().getVersion());

        return true;
    }
}