package com.takoy3466.simpleitemedit.command;

import com.takoy3466.simpleitemedit.command.common.EditCommand;
import com.takoy3466.simpleitemedit.command.common.HelpCommand;
import com.takoy3466.simpleitemedit.command.common.ResetCommand;
import com.takoy3466.simpleitemedit.editor.EditorRegistry;
import com.takoy3466.simpleitemedit.session.EditingSessionManager;
import com.takoy3466.simpleitemedit.util.EnchantLimitManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class SimpleEditCommand extends AbstractCommand {
    private final EditingSessionManager sessionManager;
    private final EditorRegistry editors;
    private final EnchantLimitManager limitManager;

    public SimpleEditCommand(EditingSessionManager sessionManager, EditorRegistry editors, EnchantLimitManager limitManager) {
        super(new CommandRegistry());

        this.sessionManager = sessionManager;
        this.editors = editors;
        this.limitManager = limitManager;

        registerCommands();
    }

    @Override
    protected void registerCommands() {
        registry.register(new EditCommand(sessionManager, editors));
        registry.register(new HelpCommand(registry, limitManager));
        registry.register(new ResetCommand());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("simpleitemedit.use")) {
            sender.sendMessage("§cYou do not have permission to use SimpleItemEdit.");
            return true;
        }

        if (args.length == 0) {
            registry.execute(sender, "edit", new String[0]);

            return true;
        }

        String subCommand = args[0];

        String[] subArgs = Arrays.copyOfRange(args, 1, args.length);

        if (!registry.execute(sender, subCommand, subArgs)) {
            sender.sendMessage("§cUnknown subcommand: " + subCommand);

            sender.sendMessage("§7Use §f/simpleEdit help");
        }

        return true;
    }
}