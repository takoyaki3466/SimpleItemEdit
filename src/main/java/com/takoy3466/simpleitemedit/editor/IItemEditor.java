package com.takoy3466.simpleitemedit.editor;

import com.takoy3466.simpleitemedit.context.IEditContext;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;

public interface IItemEditor {

    String id();

    Component displayName();

    ItemStack createIcon();

    void reset(IEditContext context);
}
