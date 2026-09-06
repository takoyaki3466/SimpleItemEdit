package com.takoy3466.simpleitemedit.context;

import com.takoy3466.simpleitemedit.editor.EditorRegistry;
import com.takoy3466.simpleitemedit.session.EditingSession;
import org.bukkit.entity.Player;

public interface IEditContext {

    Player player();

    EditingSession session();

    EditorRegistry editors();

    void refresh();

    void back();

    void close();
}
