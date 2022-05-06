package me.knighthat.vault;

import lombok.NonNull;
import org.bukkit.entity.Player;

public interface StorageImpl {

    void give(@NonNull Player target, double amount);

    boolean take(@NonNull Player target, double amount);

    void set(@NonNull Player target, double amount);

    double get(@NonNull Player target);

    default void update() {
    }
}
