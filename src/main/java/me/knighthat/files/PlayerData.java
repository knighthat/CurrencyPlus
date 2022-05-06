package me.knighthat.files;

import lombok.NonNull;
import me.knighthat.plugin.CurrencyPlus;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class PlayerData extends PluginFiles {

    private final Map<String, Double> data = new HashMap<>();

    public PlayerData(CurrencyPlus plugin) {

        super(plugin);
        load();
    }

    public void write(@NonNull Player player, double amount) {
        data.put(player.getName(), amount);
    }

    public double read(@NonNull Player player) {
        return data.getOrDefault(player.getName(), 0d);
    }

    private void dump() {
        data.forEach((name, amount) -> get().set(name, amount));
    }

    private void load() {

        data.clear();
        for (String section : get().getConfigurationSection("").getKeys(false))
            data.put(section, get().getDouble(section));
    }

    public void update() {

        try {

            dump();
            save();
            super.reload();
            load();
        } catch (NullPointerException ignored) {
        }
    }
}
