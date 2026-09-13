package com.takoy3466.simpleitemedit.editor;

import com.takoy3466.simpleitemedit.util.EditorUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public abstract class AbstractItemEditor implements IItemEditor {
    private final String id;
    private final Material iconMaterial;
    private final Component text;

    public AbstractItemEditor(String id, Material iconMaterial, String text) {
        this(id, iconMaterial, Component.text(text));
    }

    public AbstractItemEditor(String id, Material iconMaterial, Component text) {
        this.id = id;
        this.iconMaterial = iconMaterial;
        this.text = text;
    }

    @Override
    public ItemStack createIcon() {
        return EditorUtil.createIcon(iconMaterial, text);
    }

    @Override
    public Component displayName() {
        return text;
    }

    @Override
    public String id() {
        return id;
    }
}
