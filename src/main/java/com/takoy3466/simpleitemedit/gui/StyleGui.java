package com.takoy3466.simpleitemedit.gui;

import com.takoy3466.simpleitemedit.context.SimpleItemEditContext;
import com.takoy3466.simpleitemedit.context.GuiEditContext;
import com.takoy3466.simpleitemedit.context.IEditContext;
import com.takoy3466.simpleitemedit.editor.StyleEditor;
import com.takoy3466.simpleitemedit.holder.StyleGuiHolder;
import com.takoy3466.simpleitemedit.session.EditingSession;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class StyleGui implements IGui {
    private final SimpleItemEditContext context;
    private final EditingSession session;

    public StyleGui(SimpleItemEditContext context, EditingSession session) {
        this.context = context;
        this.session = session;
    }

    @Override
    public void open(Player player) {
        StyleGuiHolder holder = new StyleGuiHolder(session, context.editors());
        Inventory inventory = Bukkit.createInventory(holder, 27, Component.text("Edit Style"));
        holder.setInventory(inventory);
        setup(player, inventory, holder);
        player.openInventory(inventory);
    }

    private void setup(Player player, Inventory inventory, StyleGuiHolder holder) {
        inventory.setItem(13, session.editingItem());
        StyleEditor editor = getEditor();

        if (editor == null) {
            return;
        }

        IEditContext editorContext = new GuiEditContext(player, session, context.editors(),
                () -> new StyleGui(context, session),
                () -> new MainEditGui(session, context.editors())
        );

        inventory.setItem(10, styleButton(Material.GOLD_BLOCK, "Bold", editor.isEnabled(editorContext, TextDecoration.BOLD)));
        inventory.setItem(11, styleButton(Material.QUARTZ_BLOCK, "Italic", editor.isEnabled(editorContext, TextDecoration.ITALIC)));
        inventory.setItem(15, styleButton(Material.IRON_BARS, "Underline", editor.isEnabled(editorContext, TextDecoration.UNDERLINED)));
        inventory.setItem(16, styleButton(Material.REDSTONE, "Strikethrough", editor.isEnabled(editorContext, TextDecoration.STRIKETHROUGH)));
        inventory.setItem(20, styleButton(Material.ENDER_EYE, "Obfuscated", editor.isEnabled(editorContext, TextDecoration.OBFUSCATED)));

        inventory.setItem(22, button(Material.BARRIER, "Reset"));
        inventory.setItem(26, button(Material.ARROW, "Back"));
    }

    private StyleEditor getEditor() {

        if (!(context.editors().get("style") instanceof StyleEditor editor)) {
            return null;
        }

        return editor;
    }

    private ItemStack styleButton(Material material, String name, boolean enabled) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(Component.text(name + ": " + (enabled ? "ON" : "OFF")));
        item.setItemMeta(meta);

        return item;
    }

    private ItemStack button(Material material, String name) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(Component.text(name));
        item.setItemMeta(meta);

        return item;
    }
}