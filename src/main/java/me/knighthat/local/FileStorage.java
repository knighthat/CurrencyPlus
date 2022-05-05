package me.knighthat.local;

import lombok.NonNull;
import me.knighthat.files.PlayerData;
import me.knighthat.plugin.CurrencyPlus;
import org.bukkit.entity.Player;

public class FileStorage implements StorageImpl {

    private final @NonNull PlayerData data;

    public FileStorage(CurrencyPlus plugin) {
        this.data = new PlayerData(plugin);
    }


    @Override
    public void give(@NonNull Player target, double amount) {

        double newBalance = get(target) + amount;
        set(target, newBalance);
    }

    @Override
    public boolean take(@NonNull Player target, double amount) {

        if ((get(target) < amount))
            return false;

        double newBalance = get(target) - amount;
        set(target, newBalance);

        return true;
    }

    @Override
    public void set(@NonNull Player target, double amount) {
        data.write(target, amount);
    }

    @Override
    public double get(@NonNull Player target) {
        return data.read(target);
    }
}
