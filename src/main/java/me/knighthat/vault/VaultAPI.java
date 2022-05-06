package me.knighthat.vault;

import lombok.NonNull;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;

public class VaultAPI implements StorageImpl {

    private final @NonNull Economy economy;

    public VaultAPI(@NonNull Economy economy) {
        this.economy = economy;
    }

    @Override
    public void give(@NonNull Player target, double amount) {

    }

    @Override
    public boolean take(@NonNull Player target, double amount) {
        return false;
    }

    @Override
    public void set(@NonNull Player target, double amount) {

    }

    @Override
    public double get(@NonNull Player target) {
        return 0;
    }
}
