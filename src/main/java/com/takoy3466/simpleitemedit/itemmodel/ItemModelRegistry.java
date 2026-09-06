package com.takoy3466.simpleitemedit.itemmodel;

import org.bukkit.Material;
import org.bukkit.plugin.Plugin;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

public class ItemModelRegistry {
    private final Set<String> models = new LinkedHashSet<>();

    public void registerVanillaModels() {
        for (Material material : Material.values()) {

            if (!material.isItem()) {
                continue;
            }

            String key = material.getKey().getKey();
            models.add(key);
        }
    }

    public void register(String model) {
        if (model == null || model.isBlank()) {
            return;
        }

        models.add(model);
    }

    public void registerAll(Collection<String> models) {
        for (String model : models) {
            register(model);
        }
    }

    public void loadConfig(Plugin plugin) {
        plugin.saveDefaultConfig();
        registerAll(plugin.getConfig().getStringList("item-models"));
    }

    public void unregister(String model) {
        models.remove(model);
    }

    public boolean contains(String model) {
        return models.contains(model);
    }

    public Collection<String> getModels() {
        return Set.copyOf(models);
    }

    public void clear() {
        models.clear();
    }
}