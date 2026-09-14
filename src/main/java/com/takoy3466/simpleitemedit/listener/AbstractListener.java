package com.takoy3466.simpleitemedit.listener;

import com.takoy3466.simpleitemedit.context.GuiEditContext;
import com.takoy3466.simpleitemedit.context.IEditContext;
import com.takoy3466.simpleitemedit.context.SimpleItemEditContext;
import com.takoy3466.simpleitemedit.editor.AbstractItemEditor;
import com.takoy3466.simpleitemedit.editor.IItemEditor;
import com.takoy3466.simpleitemedit.gui.MainEditGui;
import com.takoy3466.simpleitemedit.gui.StyleGui;
import com.takoy3466.simpleitemedit.holder.AbstractGuiHolder;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractListener<G extends AbstractGuiHolder, E extends AbstractItemEditor> implements Listener {
    protected final SimpleItemEditContext context;
    private final String editorId;


    public AbstractListener(SimpleItemEditContext context, String editorId) {
        this.context = context;
        this.editorId = editorId;
    }

    protected abstract void onClick(InventoryClickEvent event);

    protected abstract void reset(@NotNull Player player, @NotNull G holder, @NotNull E editor);

    protected abstract void back(@NotNull Player player, @NotNull G holder, @NotNull E editor);

    protected IEditContext createContext(@NotNull Player player, @NotNull G holder) {
        return new GuiEditContext(player, holder.session(), context.editors(),
                () -> new StyleGui(context, holder.session()),
                () -> new MainEditGui(holder.session(), context.editors()));
    }

    public IItemEditor getEditor() {
        return context.editors().get(editorId);
    }
}
