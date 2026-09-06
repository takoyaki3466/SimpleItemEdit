package com.takoy3466.simpleitemedit.command.common;

import com.takoy3466.simpleitemedit.command.ISimpleCommand;
import com.takoy3466.simpleitemedit.editor.EditorRegistry;
import com.takoy3466.simpleitemedit.gui.MainEditGui;
import com.takoy3466.simpleitemedit.session.EditingSession;
import com.takoy3466.simpleitemedit.session.EditingSessionManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class EditCommand implements ISimpleCommand {

    private final EditingSessionManager sessionManager;
    private final EditorRegistry editors;

    public EditCommand(EditingSessionManager sessionManager, EditorRegistry editors) {
        this.sessionManager = sessionManager;
        this.editors = editors;
    }

    @Override
    public String name() {
        return "edit";
    }

    @Override
    public String description() {
        return "Open the item editor.";
    }

    @Override
    public String permission() {
        return "simpleitemedit.use";
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cOnly players can use this command.");
            return true;
        }

        ItemStack item = player.getInventory().getItemInMainHand();

        if (item.isEmpty()) {
            player.sendMessage("§cYou must hold an item in your main hand.");
            return true;
        }

        int sourceSlot = player.getInventory().getHeldItemSlot();

        EditingSession session = new EditingSession(player, sourceSlot, item);

        sessionManager.start(session);

        MainEditGui gui = new MainEditGui(session, editors);
        gui.open(player);

        return true;
    }
}