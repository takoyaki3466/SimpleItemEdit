package com.takoy3466.simpleitemedit.command;

import com.takoy3466.simpleitemedit.command.admin.ReloadCommand;
import com.takoy3466.simpleitemedit.command.admin.VersionCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class SimpleAdminEditCommand extends AbstractCommand {

    public SimpleAdminEditCommand() {
        super(new CommandRegistry());
        registerCommands();
    }

    @Override
    protected void registerCommands() {
        registry.register(new ReloadCommand());
        registry.register(new VersionCommand());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("simpleitemedit.admin")) {
            sender.sendMessage("§cYou do not have permission to use this command.");

            return true;
        }

        if (args.length == 0) {
            sendHelp(sender);
            return true;
        }

        String subCommand = args[0];

        String[] subArgs = Arrays.copyOfRange(args, 1, args.length);

        if (!registry.execute(sender, subCommand, subArgs)) {
            sender.sendMessage("§cUnknown subcommand: " + subCommand);

            sendHelp(sender);
        }

        return true;
    }

    private void sendHelp(CommandSender sender) {
        sender.sendMessage("");
        sender.sendMessage("§6===== SimpleItemEdit Admin =====");

        for (ISimpleCommand command : registry.getCommands()) {

            if (command.permission() != null && !sender.hasPermission(command.permission())) {
                continue;
            }

            sender.sendMessage("§e/SimpleAdminEdit " + command.name() + " §7- " + command.description());
        }

        sender.sendMessage("");
    }
}