package me.knighthat.local;

import lombok.NonNull;
import me.knighthat.plugin.CurrencyPlus;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

public class ContainerStorage implements StorageImpl {

    private final CurrencyPlus plugin;

    private final @NonNull NamespacedKey key;
    private final @NonNull PersistentDataType<Double, Double> type = PersistentDataType.DOUBLE;

    public ContainerStorage(CurrencyPlus plugin) {

        this.plugin = plugin;
        this.key = new NamespacedKey(plugin, "CurrencyPlus_LOCAL");
    }

    @Override
    public void give(@NonNull Player target, double amount) {

        double newBalance = get(target) + amount;
        set(target, newBalance);
    }

    @Override
    public boolean take(@NonNull Player target, double amount) {

        if (get(target) < amount)
            return false;

        double newBalance = get(target) - amount;
        set(target, newBalance);

        return true;
    }

    @Override
    public void set(@NonNull Player target, double amount) {
        target.getPersistentDataContainer().set(key, type, amount);
    }

    @Override
    public double get(@NonNull Player target) {

        if (!hasBank(target))
            return target.getPersistentDataContainer().get(key, type);

        return 0d;
    }

    private boolean hasBank(@NonNull Player target) {
        return target.getPersistentDataContainer().has(key, type);
    }
}
