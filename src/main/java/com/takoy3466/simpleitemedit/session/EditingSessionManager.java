package com.takoy3466.simpleitemedit.session;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EditingSessionManager {

    private final Map<UUID, EditingSession> sessions = new HashMap<>();

    public void start(EditingSession session) {
        sessions.put(session.player().getUniqueId(), session);
    }

    public EditingSession get(Player player) {
        return sessions.get(player.getUniqueId());
    }

    public boolean has(Player player) {
        return sessions.containsKey(player.getUniqueId());
    }

    public void remove(Player player) {
        sessions.remove(player.getUniqueId());
    }

    public void clear() {
        sessions.clear();
    }
}