package com.takoy3466.simpleitemedit.input;

import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.function.Consumer;

public interface IInputHandler {

    String id();

    void start(Player player, Consumer<String> callback);

    void start(Player player, Collection<String> completions, Consumer<String> callback);

    void cancel(Player player);

    boolean isWaiting(Player player);
}
