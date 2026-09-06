package com.takoy3466.simpleitemedit.command.common;

import com.takoy3466.simpleitemedit.command.ISimpleCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ResetCommand implements ISimpleCommand {

    @Override
    public String name() {
        return "reset";
    }

    @Override
    public String description() {
        return "Reset the edited item.";
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cThis command can only be used by players.");
            return true;
        }

        player.sendMessage("§eReset functionality is not implemented yet.");

        return true;
    }
}