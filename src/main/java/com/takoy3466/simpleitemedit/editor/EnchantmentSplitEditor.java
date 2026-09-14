package com.takoy3466.simpleitemedit.editor;

import com.takoy3466.simpleitemedit.context.IEditContext;
import org.bukkit.Material;

public class EnchantmentSplitEditor extends AbstractItemEditor {

    public EnchantmentSplitEditor() {
        super("enchantment_split", Material.ENCHANTED_BOOK, "エンチャント本の分割");
    }

    @Override
    public void reset(IEditContext context) {
        // 分割GUI専用の状態なので、
        // 現時点ではここでは何もしない。
    }
}