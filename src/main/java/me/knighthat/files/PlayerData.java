package me.knighthat.files;

import lombok.NonNull;
import me.knighthat.plugin.CurrencyPlus;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class PlayerData extends PluginFiles {

    private final Map<Player, Double> data = new HashMap<>();

    public PlayerData(CurrencyPlus plugin) {
        super(plugin);
    }

    public void write(@NonNull Player player, double amount) {
        data.put(player, amount);
    }

    public double read(@NonNull Player player) {

        return data.getOrDefault(player, 0d);
    }
}
