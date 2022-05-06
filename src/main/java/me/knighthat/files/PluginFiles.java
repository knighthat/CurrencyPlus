package me.knighthat.files;

import lombok.Getter;
import lombok.NonNull;
import me.knighthat.plugin.CurrencyPlus;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Locale;

public abstract class PluginFiles {

    @Getter
    private final CurrencyPlus plugin;
    private File file;
    private FileConfiguration yaml;

    protected PluginFiles(CurrencyPlus plugin) {
        this.plugin = plugin;
        startup();
    }

    protected @NonNull String getFileName() {
        return getClass().getSimpleName().toLowerCase(Locale.ROOT) + ".yml";
    }

    private void startup() {

        file = new File(plugin.getDataFolder(), getFileName());
        createIfNotExists();

        reload();
    }

    public void reload() {

        createIfNotExists();

        yaml = YamlConfiguration.loadConfiguration(file);
    }

    public boolean save() {

        if (file == null || yaml == null)
            reload();

        try {

            yaml.save(file);
            return true;
        } catch (IOException e) {

            String message = "Could not save {0} due to: {1}";
            message = MessageFormat.format(message, getFileName(), e.getMessage());
            plugin.getLogger().finer(message);

            e.printStackTrace();
            return false;
        }
    }

    public @NonNull FileConfiguration get() {

        if (yaml == null) reload();

        return yaml;
    }

    private void createIfNotExists() {

        if (file == null)
            startup();

        if (!file.exists())
            plugin.saveResource(getFileName(), false);
    }

}
