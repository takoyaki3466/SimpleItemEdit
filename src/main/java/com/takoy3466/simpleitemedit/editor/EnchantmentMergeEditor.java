package com.takoy3466.simpleitemedit.editor;

import com.takoy3466.simpleitemedit.context.IEditContext;
import org.bukkit.Material;

public class EnchantmentMergeEditor extends AbstractItemEditor {
    public EnchantmentMergeEditor() {
        super("enchantment_merge", Material.BOOK, "Combine Enchantment Books");
    }

    @Override
    public void reset(IEditContext context) {

    }
}