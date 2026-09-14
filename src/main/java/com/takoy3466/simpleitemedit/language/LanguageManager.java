package com.takoy3466.simpleitemedit.language;

import net.kyori.adventure.text.Component;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class LanguageManager {
    private final Plugin plugin;
    private YamlConfiguration language;

    public LanguageManager(Plugin plugin) {
        this.plugin = plugin;
        reload();
    }

    public void reload() {
        String languageName = plugin.getConfig().getString("language", "ja");

        File file = new File(plugin.getDataFolder(), "lang/" + languageName + ".yml");

        if (!file.exists()) {
            plugin.saveResource("lang/" + languageName + ".yml", false);
        }

        language = YamlConfiguration.loadConfiguration(file);

        // jar内のデフォルト翻訳を読み込む
        String resourcePath = "lang/" + languageName + ".yml";

        InputStream stream = plugin.getResource(resourcePath);

        if (stream != null) {
            YamlConfiguration defaults = YamlConfiguration.loadConfiguration(new InputStreamReader(stream, StandardCharsets.UTF_8));

            language.setDefaults(defaults);
        }
    }

    public String text(String key) {
        return language.getString(key, key);
    }

    public Component component(String key, Object... replacements) {
        String text = text(key);

        for (int i = 0; i + 1 < replacements.length; i += 2) {
            String placeholder = String.valueOf(replacements[i]);
            String value = String.valueOf(replacements[i + 1]);

            text = text.replace("{" + placeholder + "}", value);
        }

        return Component.text(text);
    }
}
