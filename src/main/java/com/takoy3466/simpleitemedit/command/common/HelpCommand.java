package com.takoy3466.simpleitemedit.command.common;

import com.takoy3466.simpleitemedit.command.CommandRegistry;
import com.takoy3466.simpleitemedit.command.ISimpleCommand;
import com.takoy3466.simpleitemedit.util.EnchantLimitManager;
import net.kyori.adventure.text.Component;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.command.CommandSender;

import java.util.Map;

public class HelpCommand implements ISimpleCommand {

    private final CommandRegistry registry;
    private final EnchantLimitManager limitManager;

    public HelpCommand(CommandRegistry registry, EnchantLimitManager limitManager) {
        this.registry = registry;
        this.limitManager = limitManager;
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
        sendEnchantmentLimits(sender);
        sender.sendMessage("");

        return true;
    }

    private void sendEnchantmentLimits(CommandSender sender) {
        sender.sendMessage(Component.text("§6===== エンチャントレベル上限 ====="));

        for (Map.Entry<Enchantment, Integer> entry : limitManager.getLimits().entrySet()) {
            Enchantment enchantment = entry.getKey();
            int limit = entry.getValue();

            Component name = getEnchantmentName(enchantment);
            sender.sendMessage(Component.text("§e").append(name).append(Component.text(" §7: §f" + formatLimit(limit))));
        }

        Component unbreakingName = getEnchantmentName(Enchantment.UNBREAKING);
        sender.sendMessage(Component.text("§e").append(unbreakingName).append(Component.text(" §7:")));
        sender.sendMessage(Component.text("  §7防具: §f" + formatLimit(limitManager.getUnbreakingArmorLimit())));
        sender.sendMessage(Component.text("  §7クロスボウ: §f" + formatLimit(limitManager.getUnbreakingCrossbowLimit())));
        sender.sendMessage(Component.text("  §7トライデント: §f" + formatLimit(limitManager.getUnbreakingTridentLimit())));
        sender.sendMessage(Component.text("  §7その他: §f" + formatLimit(limitManager.getUnbreakingOtherLimit())));
    }

    private Component getEnchantmentName(Enchantment enchantment) {
        String key = enchantment.getKey().getKey();

        return Component.translatable("enchantment.minecraft." + key);
    }

    private String formatLimit(int limit) {
        return limit < 0 ? "無制限" : String.valueOf(limit);
    }
}
