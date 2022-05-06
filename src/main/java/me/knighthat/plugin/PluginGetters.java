package me.knighthat.plugin;

import lombok.NonNull;
import me.knighthat.files.Config;
import me.knighthat.files.Messages;
import me.knighthat.vault.StorageImpl;

public interface PluginGetters {

    @NonNull CurrencyPlus getPlugin();

    default @NonNull Messages getMessages() {
        return getPlugin().messages;
    }

    default @NonNull Config getConfig() {
        return getPlugin().config;
    }

    default @NonNull StorageImpl getVault() {
        return getPlugin().vault;
    }
}
